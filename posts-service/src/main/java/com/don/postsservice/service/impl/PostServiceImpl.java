package com.don.postsservice.service.impl;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.page.PagedResponse;
import com.don.postsservice.dto.request.PostCreateRequest;
import com.don.postsservice.dto.request.enums.EPostSorting;
import com.don.postsservice.exception.badrequest.GeneralBadRequest;
import com.don.postsservice.exception.message.EErrorMessage;
import com.don.postsservice.mapper.PostMapper;
import com.don.postsservice.mapper.PostMapperService;
import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;
import com.don.postsservice.repository.PostRepository;
import com.don.postsservice.service.LikeService;
import com.don.postsservice.service.PhotoService;
import com.don.postsservice.service.PostService;
import com.don.postsservice.service.UserExtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor @Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PhotoService photoService;
    private final UserExtService userService;
    private final PostMapperService postMapperService;
    private final LikeService likeService;
    private final TransactionHelperServiceImpl transactionHelper;
    private final EntityManager entityManager;

    // TODO: make is so that in response i send the post photos as well, not sure . make is so that we do not send user data in response (unnecessary)
    // no need for @Transactional here. we only do processing, 1 SELECT (userExt, which we do no modify) and only
    // in the end we save the post (and children, bcs we use .PERSIST) (the save will by default be Transactional in the JpaRepository)
    @Override
    @Transactional(propagation = Propagation.NEVER)
    public PostDTO addPost(final PostCreateRequest postCreateRequest, final MultipartFile[] uploadedPhotos) {
        Post post = new Post();
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setTimeCreated(LocalDateTime.now());
        post.setDescription(postCreateRequest.getDescription());

        /*
        since the user already exists and we just want to set the relationship between the user and post, it is not necessary
        to SELECT the user, bcs we will not use any of its fields. in cases like this where we only care about setting the relationship (practically we just want the new post
        to be saved with the FK of user) we can user .getById(ID) -> Instead of generating and executing a database query, Hibernate only instantiates and returns a proxy object
        using the provided primary key value, meaning no SELECT is done, but only a proxy object with only the ID field is created.
        When the Post and Photos are persisted, the UserExt record will no be changed at all. However, the inserted Post with have userId as FK.
        */
        final UserExt user = verifyIdAndGetLoggedUserReference(postCreateRequest);
        post.setUser(user);

        photoService.addUploadedPhotosIntoPost(uploadedPhotos, post);

        // no need for @Transactional here, bcs since we use PERSIST strategy, we save both Parent and children together with 1 method, and by itself save() is transactional,
        // so if either Post or any of children don't get inserted correctly, the other inserts are rolled-back.
        final Post savedPost = postRepository.save(post); // returns the same post object that we save, but now it has id (also the photos also have their ids now). no extra select is done after the inserts.

        final Map<Long, byte[]> photosData = photoService.retrievePhotosData(List.of(savedPost));
        System.out.println("WTF happened");
        return postMapperService.toPostDTOWithPhotos(savedPost, photosData);
    }

    // TODO: this will need the users data in response
    @Override
    public PagedResponse<PostDTO> getPosts(final Integer pageNo, final Integer pageSize, final EPostSorting sortBy) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public PagedResponse<PostDTO> getUserPosts(final Long userId, final Integer pageNo,
                                               final Integer pageSize, final EPostSorting sortBy) {

        userService.verifyUserExists(userId); // throws if not exists
        final Sort sorting = Sort.by(sortBy.getPostFieldName()).descending();
        final Pageable paging = PageRequest.of(pageNo, pageSize, sorting);

        // retrieve the posts page (post is retrieved without User, since the client already has the user data)
        final Page<Post> postsPage = transactionHelper.withTransactionReadOnly(
                () -> postRepository.findAllPostsOfUser(paging, userId)
        );
        // Find the posts that user has liked, so that we can later on put likedByUser=true in the DTO
        final Long loggedUserId = userService.getLoggedUserId();
        final List<Long> postIds = transactionHelper.withTransactionReadOnly(
                () -> likeService.getPostIdsLikedByUser(loggedUserId)
        );

        final List<Post> posts = postsPage.getContent();
        final Map<Long, byte[]> photosData = photoService.retrievePhotosData(posts);

        final List<PostDTO> postsOfUser = postMapperService.toPostDTOsWithPhotos(posts, photosData);

        for (var post : postsOfUser) {
            if (postIds.contains(post.getPostId()))
                post.setLikedByUser(true);
        }
        return new PagedResponse<>(postsOfUser, postsPage.getSize(), postsPage.getTotalElements());
    }


/*

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public PagedResponse<PostDTO> getUserPosts(final Long userId, final Integer pageNo,
                                               final Integer pageSize, final EPostSorting sortBy) {

        userService.verifyUserExists(userId); // throws if not exists
        final Sort sorting = Sort.by(sortBy.getPostFieldName()).descending();
        final Pageable paging = PageRequest.of(pageNo, pageSize, sorting);

        // retrieve the posts page (post is retrieved without User, since the client already has the user data)
        final Page<Post> postsPage = postRepository.findAllPostsOfUser(paging, userId);
        // Find the posts that user has liked, so that we can later on put likedByUser=true in the DTO
        final List<Long> postIds = likeService.getPostIdsLikedByUser(userService.getLoggedUserId());

        //List<Post> posts = postsPage.getContent();
        //photoService.addUploadedPhotosIntoPost();
        final List<PostDTO> postsOfUser = postMapper.toPostDTOs(postsPage.getContent());

        for (var post : postsOfUser) {
            if (postIds.contains(post.getPostId()))
                post.setLikedByUser(true);
        }
        return new PagedResponse<>(postsOfUser, postsPage.getSize(), postsPage.getTotalElements());
    }
*/

    private UserExt verifyIdAndGetLoggedUserReference(final PostCreateRequest postCreateRequest) {
        final Long loggedUserId = userService.getLoggedUserId();
        final Long requestUserId = postCreateRequest.getUserId();
        if (!requestUserId.equals(loggedUserId)) {
            log.error("The currently logged in user does not correspond with the user id in the post creation request");
            throw new GeneralBadRequest(EErrorMessage.USER_IDS_DO_NOT_MATCH.getMessage(), requestUserId.toString());
        }
        return userService.getUserReference(loggedUserId);
    }

}

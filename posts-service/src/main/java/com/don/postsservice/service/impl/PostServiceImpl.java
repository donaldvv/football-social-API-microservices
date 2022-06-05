package com.don.postsservice.service.impl;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.page.PagedResponse;
import com.don.postsservice.dto.request.PostCreateRequest;
import com.don.postsservice.dto.request.enums.EPostSorting;
import com.don.postsservice.exception.badrequest.GeneralBadRequest;
import com.don.postsservice.exception.message.EErrorMessage;
import com.don.postsservice.mapper.PostMapper;
import com.don.postsservice.model.Photo;
import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;
import com.don.postsservice.repository.LikeRepository;
import com.don.postsservice.repository.PostRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor @Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PhotoService photoService;
    private final UserExtService userService;
    private final PostMapper postMapper;
    private final LikeRepository likeRepository;

    // no need for @Transactional here. we only do processing, 1 SELECT (userExt, which we do no modify) and only
    // in the end we save the post (and children, bcs we use .MERGE) (the save will by default be Transactional in the JpaRepository)
    @Override
    public PostDTO addPost(final PostCreateRequest postCreateRequest, final MultipartFile[] uploadedPhotos) {
        Post post = new Post();
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setTimeCreated(LocalDateTime.now());
        post.setDescription(postCreateRequest.getDescription());

        final UserExt user = verifyIdAndGetLoggedUser(postCreateRequest);
        post.setUser(user);

        photoService.addProcessedPhotosIntoPost(uploadedPhotos, post);

        // no need for @Transactional here, bcs since we use PERSIST strategy, we save both Parent and children together with 1 method, and by itself save() is transactional,
        // so if either Post or any of children don't get inserted correctly, the other inserts are rolled-back.
        final Post savedPost = postRepository.save(post); // returns the same post object that we save, but now it has id (also the photos also have their ids now). no extra select is done after the inserts.

        return postMapper.postToPostDTO(savedPost);
    }

    // TODO:
    @Override
    public PagedResponse<PostDTO> getPosts(final Integer pageNo, final Integer pageSize, final EPostSorting sortBy) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<PostDTO> getUserPosts(final Long userId, final Integer pageNo,
                                               final Integer pageSize, final EPostSorting sortBy) {
        final UserExt user = userService.getUser(userId);
        final Sort sorting = Sort.by(sortBy.getPostFieldName()).descending();
        final Pageable paging = PageRequest.of(pageNo, pageSize, sorting);

        final Page<Post> postsPage = postRepository.findAllPostsOfUser(paging, user); // TODO: check if thhe user is also retirieved from the query. fetch type is lazy but maybe: WHERE p.user = :user ?
        final List<PostDTO> postsOfUser = postMapper.toPostDTOs(postsPage.getContent());

        // Find the posts that user has liked, and put likedByUser=true, when finding them into the List<PostOfAnUserDTO>
        final List<Long> postIds = likeRepository.findPostIdsLikedByUser(userService.getLoggedUser());
        for (var post : postsOfUser) {
            if (postIds.contains(post.getPostId()))
                post.setLikedByUser(true);
        }
        return new PagedResponse<>(postsOfUser, postsPage.getSize(), postsPage.getTotalElements());
    }

    private UserExt verifyIdAndGetLoggedUser(final PostCreateRequest postCreateRequest) {
        final UserExt user = userService.getLoggedUser();
        final Long requestUserId = postCreateRequest.getUserId();
        if (!requestUserId.equals(user.getUserIdExt())) {
            log.error("The currently logged in user does not correspond with the user id in the post creation request");
            throw new GeneralBadRequest(EErrorMessage.USER_IDS_DO_NOT_MATCH.getMessage(), requestUserId.toString());
        }
        return user;
    }

}

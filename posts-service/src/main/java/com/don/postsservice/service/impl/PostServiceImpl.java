package com.don.postsservice.service.impl;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.request.PostCreateRequest;
import com.don.postsservice.model.Photo;
import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;
import com.don.postsservice.repository.PostRepository;
import com.don.postsservice.service.PhotoService;
import com.don.postsservice.service.PostService;
import com.don.postsservice.service.UserExtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserExtService userExtService;
    //private final PostMapper postMapper;

    // no need for @Transactional here. we only do processing, 1 SELECT (userExt, which we do no modify) and only
    // in the end we save the post (and children, bcs we use .MERGE) (the save will by default be Transactional in the JpaRepository)
    @Override
    public PostDTO addPost(final PostCreateRequest postCreateRequest, final MultipartFile[] uploadedPhotos) {
        Post post = new Post();
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setTimeCreated(LocalDateTime.now());
        post.setDescription(postCreateRequest.getDescription());
        List<Photo> photos = photoService.getProcessedPhotos(uploadedPhotos, post);
        post.setPhotos(photos); // set the children
        UserExt user = userExtService.getLoggedUser();
        post.setUserExt(user);

        Post savedPost = postRepository.save(post);
        return null;//postMapper.toPostDTOWithAllDetails(savedPost); // hidhi nje sy prap se mbase nuk eshte mapuar sakte lista e Photove ne DTO

    }
}

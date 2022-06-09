package com.don.postsservice.service.impl;

import com.don.postsservice.model.Like;
import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;
import com.don.postsservice.repository.LikeRepository;
import com.don.postsservice.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public void verifyLikeNotExists(Post post, UserExt user) {
        if (likeRepository.existsByPostAndUser(post, user)) {
            log.error("User with id: {}, has already liked the post with id: {}", user.getId(), post.getId());
            //throw new BadRequestException(String.format("User has already liked the post with id: %s!", post.getId()));
        }
    }

    @Override
    public Like addLike(UserExt user, Post post) {
        // create a Like entity with post and user
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        return likeRepository.save(like);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getPostIdsLikedByUser(final Long userId) {
        return likeRepository.findPostIdsLikedByUser(userId);
    }
}

package com.don.postsservice.service;

import com.don.postsservice.model.Like;
import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;

import java.util.List;

public interface LikeService {

    void verifyLikeNotExists(Post post, UserExt user);

    Like addLike(UserExt user, Post post);

    List<Long> getPostIdsLikedByUser(final Long userId);
}

package com.don.postsservice.repository;

import com.don.postsservice.model.Like;
import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByPostAndUserExt(Post post, UserExt user);

    @Query("SELECT l.post.id FROM Like l WHERE l.user = :user ")
    List<Long> findPostIdsLikedByUser(UserExt user);
}

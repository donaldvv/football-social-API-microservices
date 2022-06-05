package com.don.postsservice.repository;

import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Donald Veizi
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value =
            " SELECT p                  " +
            " FROM Post p               " +
            " LEFT JOIN FETCH p.photos  " +
            " WHERE p.user = :user      ",
            countQuery = "select count(p) from Post p where p.user = :user")
    Page<Post> findAllPostsOfUser(Pageable pageable, @Param("user") UserExt user);

}

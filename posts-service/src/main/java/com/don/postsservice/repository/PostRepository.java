package com.don.postsservice.repository;

import com.don.postsservice.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Donald Veizi
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    // user is not fetched in this case
    @Query(value =
            " SELECT p                  " +
                    " FROM Post p               " +
                    " LEFT JOIN FETCH p.photos  " +
                    " WHERE p.user.userIdExt = :userId      ",
            countQuery = "select count(p) from Post p where p.user.userIdExt = :userId")
    Page<Post> findAllPostsOfUser(Pageable pageable, @Param("userId") Long userId);

}

package com.don.postsservice.repository;

import com.don.postsservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Donald Veizi
 */
public interface PostRepository extends JpaRepository<Post, Long> {


}

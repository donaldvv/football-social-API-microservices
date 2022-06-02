package com.don.postsservice.service;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.request.PostCreateRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Donald Veizi
 */
public interface PostService {

    PostDTO addPost(final PostCreateRequest postCreateRequest, final MultipartFile[] pictures);
}

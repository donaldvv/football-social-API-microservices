package com.don.postsservice.service;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.page.PagedResponse;
import com.don.postsservice.dto.request.PostCreateRequest;
import com.don.postsservice.dto.request.enums.EPostSorting;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Donald Veizi
 */
public interface PostService {

    PostDTO addPost(final PostCreateRequest postCreateRequest, final MultipartFile[] photos);

    PagedResponse<PostDTO> getPosts(final Integer pageNo, final Integer pageSize, final EPostSorting sortBy);

    PagedResponse<PostDTO> getUserPosts(final Long userId, final Integer pageNo, final Integer pageSize, final EPostSorting sortBy);

}

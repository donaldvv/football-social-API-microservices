package com.don.postsservice.service.impl;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.request.PostCreateRequest;
import com.don.postsservice.repository.PostRepository;
import com.don.postsservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor @Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;


    @Override
    public PostDTO addPost(final PostCreateRequest postCreateRequest, final MultipartFile[] pictures) {
        return null;
    }
}

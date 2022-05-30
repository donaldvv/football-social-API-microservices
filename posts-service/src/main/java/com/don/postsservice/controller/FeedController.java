package com.don.postsservice.controller;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.request.PostCreateRequest;
import com.don.postsservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author Donald Veizi
 */
@RestController
@RequiredArgsConstructor
@Validated
public class FeedController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostDTO> addPost(
            @RequestPart @Valid PostCreateRequest postCreateRequest,
            @RequestPart MultipartFile[] pictures) {
        PostDTO createdPost = postService.addPost(postCreateRequest, pictures);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}

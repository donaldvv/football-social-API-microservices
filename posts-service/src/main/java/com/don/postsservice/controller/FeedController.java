package com.don.postsservice.controller;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.request.PostCreateRequest;
import com.don.postsservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
@CrossOrigin
public class FeedController {

    private final PostService postService;

    @Operation(summary = "Add a new Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created post"),
            @ApiResponse(responseCode = "400", description = "There was a problem in the given request, the given " +
                    "parameters have not passed the required validations")
    })
    @PostMapping("/posts")
    public ResponseEntity<PostDTO> addPost(
            @RequestPart @Valid PostCreateRequest postCreateRequest,
            @RequestPart MultipartFile[] photos) {
        PostDTO createdPost = postService.addPost(postCreateRequest, photos);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}

package com.don.postsservice.controller;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.page.PagedResponse;
import com.don.postsservice.dto.request.PostCreateRequest;
import com.don.postsservice.dto.request.enums.EPostSorting;
import com.don.postsservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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
    @PreAuthorize("hasRole('RECRUITER') or hasRole('USER')")
    public ResponseEntity<PostDTO> addPost(
            @RequestPart @Valid PostCreateRequest postCreateRequest,
            @RequestPart MultipartFile[] photos) {
        final PostDTO createdPost = postService.addPost(postCreateRequest, photos);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // TODO: no logic in service
    @Operation(summary = "Get posts of user's connections")
    @ApiResponse(responseCode = "200", description = "Successfully got the posts")
    @GetMapping("/posts")
    public PagedResponse<PostDTO> getPosts(
            @RequestParam(defaultValue = "0") @Min(0) @Parameter(name = "pageNo", description = "The page number", in = ParameterIn.PATH,
                    schema = @Schema(type = "integer")) Integer pageNo,
            @RequestParam(defaultValue = "10") @Min(1) @Parameter(name = "pageSize", description = "The size of the page", in = ParameterIn.PATH,
                    schema = @Schema(type = "integer")) Integer pageSize,
            // use enum for sorting. the default value is just the string representation of the one of the enum constants
            @RequestParam(required = false, defaultValue = "RECENT") @Parameter(name = "pageSize", description = "The sorting of posts", in = ParameterIn.PATH,
                    schema = @Schema(type = "string", allowableValues = {"RECENT", "LIKES"})) EPostSorting sortBy) {
        return postService.getPosts(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get posts of a single user")
    @ApiResponse(responseCode = "200", description = "Successfully got the posts")
    @GetMapping("/posts/{userId}")
    public PagedResponse<PostDTO> getPostsOfUser(
            @PathVariable @Min(1) @Parameter(name = "userId", description = "The user's ID", in = ParameterIn.PATH,
                    schema = @Schema(type = "integer")) Long userId,
            @RequestParam(defaultValue = "0") @Min(0) @Parameter(name = "pageNo", description = "The page number", in = ParameterIn.PATH,
                    schema = @Schema(type = "integer")) Integer pageNo,
            @RequestParam(defaultValue = "10") @Min(1) @Parameter(name = "pageSize", description = "The size of the page", in = ParameterIn.PATH,
                    schema = @Schema(type = "integer")) Integer pageSize,
            @RequestParam(required = false, defaultValue = "RECENT") @Parameter(name = "pageSize", description = "The sorting of posts", in = ParameterIn.PATH,
                    schema = @Schema(type = "string", allowableValues = {"RECENT", "LIKES"})) EPostSorting sortBy) {
        return postService.getUserPosts(userId, pageNo, pageSize, sortBy);
    }


}

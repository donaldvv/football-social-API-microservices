package com.don.postsservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Will contain the necessary information required for showing a post.
 *
 * @author Donald Veizi
 */
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private long postId;
    private String description;
    private List<PhotoDTO> photos;
    private int likeCount;
    private int commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeCreated;
    private UserDTO postOwner;
    private boolean isLikedByUser = false;
}

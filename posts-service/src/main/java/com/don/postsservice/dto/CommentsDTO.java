package com.don.postsservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Donald Veizi
 */
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentsDTO<T> {

    private Long postId;
    private Long postCreatorUserId;

    private List<T> comments;
}

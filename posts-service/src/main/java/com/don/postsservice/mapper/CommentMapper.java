package com.don.postsservice.mapper;


import com.don.postsservice.dto.CommentDTO;
import com.don.postsservice.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
    @Mapping(target = "userId", expression = "java(comment.getUser().getUserIdExt())")
    @Mapping(target = "userFirstname", expression = "java(comment.getUser().getFirstName())")
    @Mapping(target = "userLastname", expression = "java(comment.getUser().getLastName())")
    @Mapping(target = "userPicture", expression = "java(comment.getUser().getProfilePhoto())")
    CommentDTO commentToCommentDTO(Comment comment);

    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments);
}

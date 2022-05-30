package com.don.postsservice.dto;

import com.don.postsservice.model.Photo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * @author Donald Veizi
 */
@Getter @Setter
public class PostDTO {

    private long postId;
    private String description;
    private LinkedList<Photo> photos; //
    private int likeCount;
    private int commentCount;
    private LocalDateTime timeCreated;
    private long userId;
    private String userFirstname;
    private String userLastname;
    private String userPicture;
    private boolean likedByUser = false;

}

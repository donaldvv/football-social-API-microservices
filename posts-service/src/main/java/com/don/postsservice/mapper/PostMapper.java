package com.don.postsservice.mapper;

import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
/*

    // used just for the like functionality (send it back to client to verify it) - doesnt
    // have photos, description (which means this PostDTO it is not used to show the actual posts in feed))
    @Mapping(target = "postId", source = "id")
    @Mapping(target = "userId", expression = "java(post.getUser().getId())")
    @Mapping(target = "userFirstname", expression = "java(post.getUser().getFirstName())")
    @Mapping(target = "userLastname", expression = "java(post.getUser().getLastName())")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "photos", ignore = true) // will be fetched by an extra query if not ignored, bcs this gets calls while in a transaction
    PostDTO postToPostDTO(Post post);
*/


    /**
     * Will map Post into PostDTO.
     * Ignore private UserDTO postOwner; && private List<PhotoDTO> photos;
     * @param post the post that should be mapped into DTO
     * @return {@link PostDTO}
     */
    @Named(value = "post_NoUser_NoPhotos")
    @Mapping(target = "postId", source = "id")
    @Mapping(target = "postOwner", ignore = true)
    @Mapping(target = "photos", ignore = true)
    PostDTO postToPostDTO(Post post);
/*
    @IterableMapping(qualifiedByName = "post_NoUser_NoPhotos")
    List<PostDTO> toPostDTOs(List<Post> posts);
*/
/*

    */
/**
     * Will map Post into PostDTO.
     * It does not contain comments of the actual people who liked the post (use other DTO for those).
     * The photos will be mapped automatically by the PhotoMapper.class (no need to specify
     * with @Mapping bcs both use 'photos' as field name)
     *
     * @param post the post that should be mapped into DTO
     * @return {@link PostDTO}
     *//*

    @Named(value = "post_NoComments")
    @Mapping(target = "postId", source = "id")
    @Mapping(target = "postOwner", source = "user") // will be mapped by UserExtMapper.class
    PostDTO postToPostDTOWithUser(Post post);

    @IterableMapping(qualifiedByName = "post_NoComments")
    List<PostDTO> toPostDTOsWithUser(List<Post> posts);
*/

/*
    // used when getting Posts of a specific user. I do not send the user details in this one
    @Named(value = "postsOfAnUser")
    PostOfAUserDTO toPostOfAUserDTO(Post post); // the List<Photo> is mapped automatically into List<PhotoDTO>, bcs i put:  uses = {PhotoMapper.class}
    @IterableMapping(qualifiedByName = "postsOfAnUser")
    List<PostOfAUserDTO> toPostOfAUserDTOs(List<Post> posts);
*/

}

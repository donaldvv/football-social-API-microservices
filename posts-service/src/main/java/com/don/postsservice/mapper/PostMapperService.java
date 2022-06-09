package com.don.postsservice.mapper;

import com.don.postsservice.dto.PhotoDTO;
import com.don.postsservice.dto.PostDTO;
import com.don.postsservice.dto.UserDTO;
import com.don.postsservice.model.Photo;
import com.don.postsservice.model.Post;
import com.don.postsservice.model.UserExt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User for mapping posts (and related children objects) into DTOs
 * The reason we do not use only the mapstruct interface is because of the cases where we need to map List<Post> (which has as a field List<Photo>).
 * In this case it is not as straightforward because based on the name field of Photo, we retrieve the Byte[] of the actual image and need to add it
 * as a field in the PostDTO.
 *
 *
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor
public final class PostMapperService {

    private final PostMapper postMapper;
    private final PhotoMapper photoMapper;
    /*
    1- per all posts ne Feed        ->      post + user + foto
    2- per all possts of a user     ->      post + foto
    3- rasti i create qe nuk cojm gje as user as photo mdkt -> shifi nje sy a prish pun


     */

    public PostDTO toPostDTOWithPhotos(final Post post, final Map<Long, byte[]> photosData) {
        if (post == null) {
            return null;
        }
        // will ignore posts and user
        final PostDTO postDTO = postMapper.postToPostDTO(post);

        addMappedPhotos(postDTO, post, photosData);

        return postDTO;
    }

    public List<PostDTO> toPostDTOsWithPhotos(final List<Post> posts, final Map<Long, byte[]> photosData) {
        return posts.stream().map(post -> this.toPostDTOWithPhotos(post, photosData)).toList();
    }

    public PostDTO toPostDTOWithPhotosAndUser(final Post post,
                                              final Map<Long, byte[]> photosData,
                                              final Map<Long, byte[]> usersPhotoData) {
        if (post == null) {
            return null;
        }
        // will ignore posts and user
        final PostDTO postDTO = postMapper.postToPostDTO(post);

        addUser(postDTO, post, usersPhotoData);

        addMappedPhotos(postDTO, post, photosData);

        return postDTO;
    }

    public List<PostDTO> toPostDTOsWithPhotosAndUser(final List<Post> posts,
                                                     final Map<Long, byte[]> photosData,
                                                     final Map<Long, byte[]> usersPhotoData) {
        return posts.stream().map(post -> this.toPostDTOWithPhotosAndUser(post, photosData, usersPhotoData)).toList();
    }

    private void addMappedPhotos(final PostDTO postDTO, final Post post, final Map<Long, byte[]> photosData) {
        final List<Photo> photos = post.getPhotos();
        if (photos == null || photos.isEmpty() || photosData.isEmpty()) {
            postDTO.setPhotos(new ArrayList<>());
            return;
        }
        final List<PhotoDTO> photoDTOs = new LinkedList<>();
        for (var photo : photos) {
            final PhotoDTO photoDTO = photoMapper.photoToPhotoDTO(photo);
            final byte[] data = photosData.get(photo.getId());
            photoDTO.setPhotoData(data);
            photoDTOs.add(photoDTO);
        }
        postDTO.setPhotos(photoDTOs);
    }

    private void addUser(final PostDTO postDTO, final Post post, final Map<Long, byte[]> usersPhotoData) {
        final UserExt user = post.getUser();
        if (user == null)
            return;

        final UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserIdExt());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setShortSummary(user.getShortSummary());
        if (usersPhotoData == null || usersPhotoData.isEmpty()) {
            userDTO.setProfilePhotoData(null);
        } else {
            final byte[] profilePhotoData = usersPhotoData.get(user.getUserIdExt());
            userDTO.setProfilePhotoData(profilePhotoData);
        }

        postDTO.setPostOwner(userDTO);
    }

}

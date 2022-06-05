package com.don.postsservice.mapper;

import com.don.postsservice.dto.PhotoDTO;
import com.don.postsservice.model.Photo;
import org.mapstruct.Mapper;

import java.util.LinkedList;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoDTO photoToPhotoDTO(Photo photo);

    LinkedList<PhotoDTO> photosToPhotoDTOs(LinkedList<Photo> photos);
}

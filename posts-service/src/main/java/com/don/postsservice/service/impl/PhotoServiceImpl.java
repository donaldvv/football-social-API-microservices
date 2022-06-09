package com.don.postsservice.service.impl;

import com.don.postsservice.model.Photo;
import com.don.postsservice.model.Post;
import com.don.postsservice.service.PhotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.don.postsservice.utils.FileUtils.getCleanFilename;
import static com.don.postsservice.utils.FileUtils.retrievePhotoData;
import static com.don.postsservice.utils.FileUtils.uploadFile;

/**
 * @author Donald Veizi
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Value("${uploadDir}")
    private String baseUploadDir;

    public void addUploadedPhotosIntoPost(final MultipartFile[] files, final Post post) {
        if (files != null && files.length != 0) {
            for (var file : files) {
                if (!file.isEmpty()) {
                    String cleanFilename = getCleanFilename(file);
                    uploadFile(baseUploadDir, file, cleanFilename);
                    Photo photo = new Photo();
                    photo.setName(cleanFilename);

                    photo.setPost(post);
                    post.addPhoto(photo);
                }
            }
        }
    }

    @Override
    public Map<Long, byte[]> retrievePhotosData(final List<Post> posts) {
        // each post has multiple Photo objects. their name field corresponds to the name of the file saved in our local directory
        // We want to retrive the actual byte[] of each photo and create a map, so that we then can add the photo data into each PostDTO
        final Map<Long, byte[]> photoMap = new HashMap<>();
        for (var post : posts) {
            // we have LinkedList<Photo> so parallell stream would be bad
            for (var photo : post.getPhotos()) {
                final String photoPath = baseUploadDir + photo.getName();
                final byte[] photoData = retrievePhotoData(photoPath);
                photoMap.put(photo.getId(), photoData);
            }
        }
        return photoMap;
        /*
         OR we can use streams, but this would very likely be slower. there is some overhead to creating streams,
         and especially with parallelStream, bcs the threads would have to be managed in the background. the posts list
         has a small amount of items and in this case we would not benefit from parallelism
        */
        /*
        posts.parallelStream()
                .forEach(post -> {
                    // parallelStream would be slow in a LinkedList
                    post.getPhotos().forEach(photo -> {
                        final String photoPath = baseUploadDir +  photo.getName();
                        final Byte[] photoData = retrievePhotoData(photoPath);
                        photoMap.put(photo.getId(), photoData);
                    });
                });
        */
    }

}

package com.don.postsservice.service.impl;

import com.don.postsservice.model.Photo;
import com.don.postsservice.model.Post;
import com.don.postsservice.service.PhotoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.don.postsservice.utils.FileUtils.getCleanFilename;
import static com.don.postsservice.utils.FileUtils.uploadFile;

/**
 * @author Donald Veizi
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    public static final String BASE_UPLOAD_DIR = "./photos/";

    public List<Photo> getProcessedPhotos(final MultipartFile[] files , final Post post) {
        final List<Photo> photos = new ArrayList<>();
        if (files != null && files.length != 0) {
            for (var file : files) {
                if (!file.isEmpty()) {
                    String cleanFilename = getCleanFilename(file);
                    uploadFile(BASE_UPLOAD_DIR, file, cleanFilename);
                    Photo photo = new Photo();
                    photo.setName(cleanFilename);
                    photos.add(photo);
                    // we set the parent, so that the caller method can save both parent and all its children
                    photo.setPost(post);
                }
            }
        }
        return photos;
    }
}

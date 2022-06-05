package com.don.postsservice.service.impl;

import com.don.postsservice.model.Photo;
import com.don.postsservice.model.Post;
import com.don.postsservice.service.PhotoService;
import org.springframework.beans.factory.annotation.Value;
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

    public static final String BASE_UPLOAD_DIR = "C:\\Users\\User\\Pictures\\football-social-photos-dir";
    @Value("${uploadDir}")
    private String baseUploadDir;

    public void addProcessedPhotosIntoPost(final MultipartFile[] files , final Post post) {
        //final List<Photo> photos = new ArrayList<>();
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
        //return photos;
    }
}

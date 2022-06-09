package com.don.postsservice.service;

import com.don.postsservice.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Donald Veizi
 */
public interface PhotoService {

    void addUploadedPhotosIntoPost(final MultipartFile[] files , final Post post);

    Map<Long, byte[]> retrievePhotosData(final List<Post> posts);
}

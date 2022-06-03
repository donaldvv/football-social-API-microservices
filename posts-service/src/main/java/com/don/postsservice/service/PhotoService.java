package com.don.postsservice.service;

import com.don.postsservice.model.Photo;
import com.don.postsservice.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Donald Veizi
 */
public interface PhotoService {

    List<Photo> getProcessedPhotos(final MultipartFile[] files , final Post post);
}

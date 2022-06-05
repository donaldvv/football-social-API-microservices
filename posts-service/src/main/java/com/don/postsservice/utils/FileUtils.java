package com.don.postsservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author Donald Veizi
 */
@Slf4j
public class FileUtils {

    /**
     * Will save the photo in the directory specified.
     * @param uploadDir the directory
     * @param photo the photo
     * @param filename the name used when saving the photo
     */
    public static void uploadFile(final String uploadDir, final MultipartFile photo, final  String filename) {
        try {
            Path path = Paths.get(uploadDir + filename);
            //Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            byte[] bytes = photo.getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("IO Exception {}", e.getMessage());
        }
    }

    /**
     * From the original name of the photo, creates a unique name, so that we do not have any name duplication
     * @param photo {@link MultipartFile}
     * @return the name of the photo
     */
    public static String getCleanFilename(final MultipartFile photo) {
        // make the name unique, so that we don't have any cases of overriding the images (replacing)
        return LocalDateTime.now().getNano() + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
    }


}

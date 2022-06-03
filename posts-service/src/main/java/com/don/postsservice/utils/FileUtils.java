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
import java.util.Objects;

/**
 * @author Donald Veizi
 */
@Slf4j
public class FileUtils {

    public static void uploadFile(final String uploadDir, final MultipartFile picture, final  String filename) {
        try {
            Path path = Paths.get(uploadDir + filename);
            Files.copy(picture.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("IO Exception {}", e.getMessage());
        }
    }

    public static String getCleanFilename(final MultipartFile picture) {
        // make the name unique, so that we don't have any cases of overriding the images (replacing)
        return Instant.now().toString() + StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
    }
}

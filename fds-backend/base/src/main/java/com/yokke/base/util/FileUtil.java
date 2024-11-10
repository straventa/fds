package com.yokke.base.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileUtil {

    public static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex > 0) {
            return filename.substring(dotIndex).toLowerCase();
        }
        return "";
    }

    public static String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.isEmpty()) {
            int lastDotIndex = originalFilename.lastIndexOf(".");
            if (lastDotIndex != -1) {
                return originalFilename.substring(lastDotIndex + 1);
            }
        }
        return null; // No valid file extension found
    }

    public static boolean isValidImage(MultipartFile multipartFile) {
        List<String> allowedExtensions = Arrays.asList(".jpeg", ".jpg", ".png");

        String fileExtension = getFileExtension(
                Objects.requireNonNull(multipartFile.getOriginalFilename()));
        if (!allowedExtensions.contains(fileExtension)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Only accepting .jpeg, .jpg, and .png files");
        }
        return true;
    }
}
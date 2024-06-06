package com.backend.athlete.support.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileUtils {
    public static String saveFile(MultipartFile file, Path rootLocation) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        if (Files.notExists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), rootLocation.resolve(filename));
        return rootLocation.resolve(filename).toString();
    }

}

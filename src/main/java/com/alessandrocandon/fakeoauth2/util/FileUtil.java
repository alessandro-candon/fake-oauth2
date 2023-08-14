package com.alessandrocandon.fakeoauth2.util;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;

public class FileUtil {
    public static String getResourceFileAsString(String fileName) {
        try {
            File resource = new ClassPathResource(fileName).getFile();
            return new String(
                    Files.readAllBytes(resource.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

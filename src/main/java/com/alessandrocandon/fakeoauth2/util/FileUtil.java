package com.alessandrocandon.fakeoauth2.util;

import java.io.*;
import java.nio.file.Files;
import org.springframework.core.io.ClassPathResource;

public class FileUtil {
    public static String getResourceFileAsString(String fileName) {
        try {
            File resource = new ClassPathResource(fileName).getFile();
            return new String(Files.readAllBytes(resource.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.alessandrocandon.fakeoauth2.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public class FileUtil {
    public static String getResourceFileAsString(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            byte[] binaryData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return new String(binaryData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
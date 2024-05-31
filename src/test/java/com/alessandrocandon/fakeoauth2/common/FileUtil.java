package com.alessandrocandon.fakeoauth2.common;

import java.io.IOException;
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

  public static String getFilePathExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf('.') + 1);
  }
}
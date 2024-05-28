/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.util;

import com.alessandrocandon.fakeoauth2.exception.IORuntimeException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public class FileUtil {

  private FileUtil() {}

  public static String getResourceFileAsString(String fileName) {
    try {
      ClassPathResource resource = new ClassPathResource(fileName);
      byte[] binaryData = FileCopyUtils.copyToByteArray(resource.getInputStream());
      return new String(binaryData, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IORuntimeException("Cannot Read Resource File: " + fileName);
    }
  }
}

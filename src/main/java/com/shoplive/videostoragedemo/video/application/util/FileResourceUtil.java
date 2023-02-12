package com.shoplive.videostoragedemo.video.application.util;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

@Component
public class FileResourceUtil {

  public ByteArrayResource readByteArrayResourceFromPath(Path path) {
    try {
      final var bytes = Files.readAllBytes(path);
      return new ByteArrayResource(bytes);
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

}

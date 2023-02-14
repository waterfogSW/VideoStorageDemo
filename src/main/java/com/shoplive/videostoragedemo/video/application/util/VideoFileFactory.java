package com.shoplive.videostoragedemo.video.application.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VideoFileFactory {

  private final VideoStorageProperties properties;

  public VideoFileInfo create(MultipartFile file) {
    final var createPath = Paths.get(properties.getPath() + file.getOriginalFilename());
    try {
      final var path = Files.write(createPath, file.getBytes());
      return VideoFileInfo.from(path);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}

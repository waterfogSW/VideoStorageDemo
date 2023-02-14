package com.shoplive.videostoragedemo.video.application.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VideoFileStorageUtil {

  private final VideoStorageProperties properties;

  public VideoFileInfo create(MultipartFile file) {
    final var createPath = Paths.get(properties.getPath() + file.getOriginalFilename());
    checkFileExists(createPath);

    try {
      final var path = Files.write(createPath, file.getBytes());
      return VideoFileInfo.from(path);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public VideoFileResource readByteArrayResourceByVideoInfo(VideoFileInfo videoFileInfo) {

    final var path = videoFileInfo.filePath();
    final var videoFileName = path.getFileName();

    try {
      final var bytes = Files.readAllBytes(path);
      final var resource = new ByteArrayResource(bytes);
      return new VideoFileResource(videoFileName.toString(), resource);
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  private void checkFileExists(Path path) {
    if (Files.exists(path)) {
      throw new IllegalArgumentException("File already exists");
    }
  }

}

package com.shoplive.videostoragedemo.video.application.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

@Component
public class VideoFileFactory {

  public VideoFileInfo create(
      String targetFolder,
      MultipartFile file
  ) {
    final var createPath = Paths.get(targetFolder + file.getOriginalFilename());
    try {
      final var path = Files.write(createPath, file.getBytes());
      return VideoFileInfo.from(path);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}

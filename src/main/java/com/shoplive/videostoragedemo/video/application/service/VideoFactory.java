package com.shoplive.videostoragedemo.video.application.service;

import static com.shoplive.videostoragedemo.video.domain.Video.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.domain.Video;

@Component
public class VideoFactory {

  public Video create(
      String targetFolder,
      MultipartFile file
  ) {
    validateMultipartFile(file);
    final var title = file.getOriginalFilename();
    final var fileSize = file.getSize();
    final var filePath = createFile(file, Paths.get(targetFolder + file.getOriginalFilename()));
    return new Video(title, fileSize, filePath);
  }

  private static Path createFile(
      MultipartFile file,
      Path createPath
  ) {
    try {
      return Files.write(createPath, file.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private static void validateMultipartFile(MultipartFile file) {
    if (!Objects.equals(file.getContentType(), VIDEO_FORMAT)) {
      final var errorMessage = String.format("File type not supported. Use %s", VIDEO_FORMAT);
      throw new IllegalArgumentException(errorMessage);
    }

    if (file.getSize() > MAX_VIDEO_SIZE) {
      final var errorMessage = String.format("File size exceed. Should be below %s", MAX_VIDEO_SIZE);
      throw new IllegalArgumentException(errorMessage);
    }
  }

}

package com.shoplive.videostoragedemo.video.application.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetadataSavePort;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VideoFileUtil {

  private final VideoMetadataSavePort videoMetadataSavePort;
  private final VideoStorageProperties properties;
  private final FFmpegUtil ffmpegUtil;

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

  @Async
  public void resize(
      Video video,
      int width,
      int height
  ) {
    validateVideoStatus(video);
    final var inputFilePath = video.getOriginal()
                                   .filePath();
    final var outputFilePath = getResizeFilePath(inputFilePath);

    ffmpegUtil.resize(inputFilePath, outputFilePath, width, height);

    final var resizedFileInfo = VideoFileInfo.from(outputFilePath);
    video.setResizedFile(resizedFileInfo);
    videoMetadataSavePort.save(video);
  }

  private Path getResizeFilePath(Path originalPath) {
    final var outputFileName = properties.getResizePrefix() + originalPath.getFileName();
    return originalPath.getParent()
                       .resolve(outputFileName);
  }

  private void validateVideoStatus(Video video) {
    if (video.getOriginal() == null) {
      throw new IllegalStateException("Original file not found");
    }

    if (video.getResized() != null) {
      throw new IllegalStateException("Resized file already exist");
    }
  }

  private void checkFileExists(Path path) {
    if (Files.exists(path)) {
      throw new IllegalArgumentException("File already exists");
    }
  }

}

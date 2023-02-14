package com.shoplive.videostoragedemo.video.application.util;

import java.nio.file.Path;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetadataSavePort;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VideoFileResizer {

  private final VideoMetadataSavePort videoMetadataSavePort;
  private final VideoStorageProperties videoStorageProperties;
  private final FFmpegUtil ffmpegUtil;

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
    final var outputFileName = videoStorageProperties.getResizePrefix() + originalPath.getFileName();
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

}

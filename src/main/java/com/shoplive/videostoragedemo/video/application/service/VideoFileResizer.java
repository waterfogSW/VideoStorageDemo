package com.shoplive.videostoragedemo.video.application.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.shoplive.videostoragedemo.video.application.port.out.VideoSaveMetadataPort;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VideoFileResizer {

  private static final String RESIZE_FILE_PREFIX = "resized_";
  private static final String RESIZE_FAIL_ERROR_MSG = "Failed to resize video.";

  private final VideoSaveMetadataPort videoSaveMetadataPort;

  @Async
  public void resize(
      Video video,
      int width,
      int height
  ) {
    if (video.getOriginal() == null) {
      throw new IllegalStateException("Original file not found");
    }

    if (video.getResized() != null) {
      throw new IllegalStateException("Resized file already exist");
    }

    final var inputFilePath = video.getOriginal().filePath();
    final var outputFilePath = getResizeFilePath(inputFilePath);
    final var cmd = getResizeCommand(inputFilePath, outputFilePath, width, height);
    final var exitValue = process(cmd);

    if (exitValue != 0) {
      throw new RuntimeException(RESIZE_FAIL_ERROR_MSG);
    }

    final var resizedFileInfo = VideoFileInfo.from(outputFilePath);
    video.setResizedFile(resizedFileInfo);
    videoSaveMetadataPort.save(video);
  }

  private int process(String[] cmd) {
    final var processBuilder = new ProcessBuilder(cmd);

    try {
      final var process = processBuilder.start();
      process.waitFor();
      return process.exitValue();
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(RESIZE_FAIL_ERROR_MSG + ":" + e.getMessage());
    }
  }

  private Path getResizeFilePath(Path originalPath) {
    final var outputFileName = RESIZE_FILE_PREFIX + originalPath.getFileName();
    return originalPath.getParent()
                       .resolve(outputFileName);
  }

  private String[] getResizeCommand(
      Path input,
      Path output,
      int width,
      int height
  ) {
    String scale = String.format("scale=%d:%d", width, height);
    return new String[]{"ffmpeg", "-i", input.toString(), "-vf", scale, output.toString()};
  }

}

package com.shoplive.videostoragedemo.video.application.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.shoplive.videostoragedemo.video.domain.Video;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VideoFileResizer {

  private static final String RESIZE_FAIL_ERROR_MSG = "Failed to resize video.";

  @Async
  public void resize(Video video, int width, int height) {
    final var inputFilePath = video.getFilePath();
    final var outputFileName = inputFilePath.getFileName() + "_resized";
    final var outputFilePath = inputFilePath.getParent().resolve(outputFileName);
    final var cmd = getCommand(inputFilePath, outputFilePath, width, height);
    final var processBuilder = new ProcessBuilder(cmd);

    try {
      final var process = processBuilder.start();
      process.waitFor();

      if (process.exitValue() != 0) {
        throw new RuntimeException(RESIZE_FAIL_ERROR_MSG);
      }
    } catch (InterruptedException | IOException e) {
      log.info(e.getMessage());
    }
  }

  private String[] getCommand(Path input, Path output, int width, int height) {
    String scale = String.format("scale=%d:%d", width, height);
    return new String[]{"ffmpeg", "-i", input.toString(), "-vf", scale, output.toString()};
  }

}

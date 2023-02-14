package com.shoplive.videostoragedemo.video.application.util;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

@Component
public class FFmpegUtil {

  private static final String PROCESS_TERMINATION_ERROR = "Process terminated abnormally.";

  public void resize(
      Path input,
      Path output,
      int width,
      int height
  ) {
    final var cmd = getResizeCommand(input, output, width, height);

    final var processBuilder = new ProcessBuilder(cmd);

    try {
      final var process = processBuilder.start();
      process.waitFor();
      if (process.exitValue() != 0) {
        throw new RuntimeException(PROCESS_TERMINATION_ERROR);
      }
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e.getMessage());
    }
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

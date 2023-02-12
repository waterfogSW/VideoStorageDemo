package com.shoplive.videostoragedemo.video.domain;

import java.nio.file.Path;

public record VideoFileInfo(
    long fileSize,
    Path filePath
) {

  public static VideoFileInfo from(Path path) {
    return new VideoFileInfo(path.toFile().length(), path);
  }

}

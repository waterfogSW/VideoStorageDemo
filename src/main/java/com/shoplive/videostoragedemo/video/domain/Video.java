package com.shoplive.videostoragedemo.video.domain;

import java.nio.file.Path;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Video {

  public static final String VIDEO_FORMAT = "video/mp4";
  public static final Long MAX_VIDEO_SIZE = 1024L * 1024L * 100L;

  private Long id;
  private final String title;
  private final long fileSize;
  private final Path filePath;
  private final LocalDateTime createdAt;

  public Video(
      Long id,
      String title,
      long fileSize,
      Path filePath
  ) {
    this.id = id;
    this.title = title;
    this.fileSize = fileSize;
    this.filePath = filePath;
    this.createdAt = LocalDateTime.now();
  }

  public Video(
      String title,
      long fileSize,
      Path filePath
  ) {
    this(null, title, fileSize, filePath);
  }

}

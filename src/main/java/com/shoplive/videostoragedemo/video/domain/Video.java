package com.shoplive.videostoragedemo.video.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Video {

  public static final String VIDEO_FORMAT = "video/mp4";
  public static final Long MAX_VIDEO_SIZE = 1024L * 1024L * 100L;

  private final Long id;
  private final String title;
  private VideoFileInfo original;
  private VideoFileInfo resized;
  private final LocalDateTime createdAt;

  public Video(
      Long id,
      String title,
      VideoFileInfo original,
      VideoFileInfo resized,
      LocalDateTime createdAt
  ) {
    this.id = id;
    this.title = title;
    this.original = original;
    this.resized = resized;
    this.createdAt = createdAt;
  }

  public Video(
      Long id,
      String title,
      VideoFileInfo original,
      VideoFileInfo resized
  ) {
    this(id, title, original, resized, LocalDateTime.now());
  }

  public static Video from(String title) {
    return new Video(null, title, null, null);
  }

  public void setOriginalFile(VideoFileInfo originalFileInfo) {
    this.original = originalFileInfo;
  }

  public void setResizedFile(VideoFileInfo resizedFileInfo) {
    this.resized = resizedFileInfo;
  }

}

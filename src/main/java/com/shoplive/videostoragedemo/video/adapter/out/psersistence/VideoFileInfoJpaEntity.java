package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoFileInfoJpaEntity {

  private Long fileSize;

  private String filePath;

  public VideoFileInfoJpaEntity(
      long fileSize,
      String filePath
  ) {
    this.fileSize = fileSize;
    this.filePath = filePath;
  }

}

package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import org.springframework.stereotype.Component;

import com.shoplive.videostoragedemo.video.domain.Video;

@Component
public class VideoMapper {

  public VideoJpaEntity mapToJpaEntity(Video video) {
    return new VideoJpaEntity(
        video.getId() == null ? null : video.getId(),
        video.getTitle(),
        video.getFileSize(),
        video.getFilePath().toString(),
        video.getTitle()
    );
  }

}

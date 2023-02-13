package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import java.nio.file.Path;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.shoplive.videostoragedemo.video.adapter.out.psersistence.entity.VideoFileInfoJpaEntity;
import com.shoplive.videostoragedemo.video.adapter.out.psersistence.entity.VideoJpaEntity;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

@Component
public class VideoMapper {

  public VideoJpaEntity mapToJpaEntity(Video video) {
    return new VideoJpaEntity(
        video.getId() == null ? null : video.getId(),
        video.getTitle(),
        mapToJpaEntity(video.getOriginal()),
        mapToJpaEntity(video.getResized()),
        video.getCreatedAt()
             .toString()
    );
  }

  public Video mapToDomainEntity(VideoJpaEntity videoJpaEntity) {
    if (videoJpaEntity == null) {
      return null;
    }

    return new Video(
        videoJpaEntity.getId(),
        videoJpaEntity.getTitle(),
        mapToDomainEntity(videoJpaEntity.getOriginal()),
        mapToDomainEntity(videoJpaEntity.getResized()),
        LocalDateTime.parse(videoJpaEntity.getCreatedAt())
    );
  }

  private VideoFileInfoJpaEntity mapToJpaEntity(VideoFileInfo videoFileInfo) {
    if (videoFileInfo == null) {
      return null;
    }
    return new VideoFileInfoJpaEntity(
        videoFileInfo.fileSize(),
        videoFileInfo.filePath()
                     .toString()
    );
  }

  private VideoFileInfo mapToDomainEntity(VideoFileInfoJpaEntity videoFileInfoJpaEntity) {
    if (videoFileInfoJpaEntity == null) {
      return null;
    }
    return new VideoFileInfo(
        videoFileInfoJpaEntity.getFileSize(),
        Path.of(videoFileInfoJpaEntity.getFilePath())
    );
  }

}

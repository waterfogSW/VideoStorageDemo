package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import com.shoplive.videostoragedemo.common.layer.PersistenceAdapter;
import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetaDataLookUpPort;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetadataSavePort;
import com.shoplive.videostoragedemo.video.domain.Video;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class VideoPersistenceAdapterLookUpSave implements
    VideoMetadataSavePort,
    VideoMetaDataLookUpPort {

  private final VideoStorageProperties videoStorageProperties;
  private final VideoJpaRepository videoJpaRepository;
  private final VideoMapper videoMapper;

  @Override
  public Video save(Video video) {
    final var videoJpaEntity = videoMapper.mapToJpaEntity(video);
    final var savedJpaEntity = videoJpaRepository.save(videoJpaEntity);
    return videoMapper.mapToDomainEntity(savedJpaEntity);
  }

  @Override
  public Video lookUpByFileName(String fileName) {
    if (fileName.startsWith("resized_")) {
      return videoJpaRepository.findByResized_FilePath(videoStorageProperties.getPath() + fileName)
                               .map(videoMapper::mapToDomainEntity)
                               .orElseThrow(() -> new IllegalArgumentException("File not found"));
    }

    return videoJpaRepository.findByOriginal_FilePath(videoStorageProperties.getPath() + fileName)
                             .map(videoMapper::mapToDomainEntity)
                             .orElseThrow(() -> new IllegalArgumentException("File not found"));
  }

}

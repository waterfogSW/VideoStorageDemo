package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import com.shoplive.videostoragedemo.common.layer.PersistenceAdapter;
import com.shoplive.videostoragedemo.video.application.port.out.VideoLookUpMetaDataPort;
import com.shoplive.videostoragedemo.video.application.port.out.VideoSaveMetadataPort;
import com.shoplive.videostoragedemo.video.domain.Video;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class VideoPersistenceAdapter implements
    VideoSaveMetadataPort,
    VideoLookUpMetaDataPort {

  private final VideoJpaRepository videoJpaRepository;
  private final VideoMapper videoMapper;

  @Override
  public Video save(Video video) {
    final var videoJpaEntity = videoMapper.mapToJpaEntity(video);
    final var savedJpaEntity = videoJpaRepository.save(videoJpaEntity);
    return videoMapper.mapToDomainEntity(savedJpaEntity);
  }

  @Override
  public Video lookUp(String title) {
    final var videoJpaEntity = videoJpaRepository.findByTitle(title)
                                                 .orElseThrow(() -> new IllegalArgumentException("File not found"));

    return videoMapper.mapToDomainEntity(videoJpaEntity);
  }

}

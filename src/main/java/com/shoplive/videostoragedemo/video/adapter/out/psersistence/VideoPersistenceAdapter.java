package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import com.shoplive.videostoragedemo.config.layer.PersistenceAdapter;
import com.shoplive.videostoragedemo.video.application.port.out.VideoSaveMetadataPort;
import com.shoplive.videostoragedemo.video.domain.Video;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class VideoPersistenceAdapter implements VideoSaveMetadataPort {

  private final VideoJpaRepository videoJpaRepository;
  private final VideoMapper videoMapper;

  @Override
  public void save(Video video) {
    final var videoJpaEntity = videoMapper.mapToJpaEntity(video);
    videoJpaRepository.save(videoJpaEntity);
  }

}

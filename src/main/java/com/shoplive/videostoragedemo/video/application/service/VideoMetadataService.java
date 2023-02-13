package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoMetadataLookupDetailResponse;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMetadataLookupCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetaDataLookUpPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoMetadataService implements VideoMetadataLookupCommand {

  private final VideoMetaDataLookUpPort videoMetaDataLookUpPort;
  private final VideoStorageProperties videoStorageProperties;

  @Override
  public VideoMetadataLookupDetailResponse lookupDetail(long id) {
    final var video = videoMetaDataLookUpPort.lookUpById(id);
    return VideoMetadataLookupDetailResponse.of(video, videoStorageProperties.getResourceUrl());
  }

}

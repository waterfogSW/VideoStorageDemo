package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.config.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoSaveMetadataPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoMultipartUploadService implements VideoMultipartUploadCommand {

  private final VideoStorageProperties videoStorageProperties;
  private final VideoSaveMetadataPort videoSaveMetadataPort;
  private final VideoFactory videoFactory;

  @Override
  @Transactional
  public void upload(MultipartFile multipartFile) {
    final var video = videoFactory.create(videoStorageProperties.getPath(), multipartFile);
    videoSaveMetadataPort.save(video);
  }

}

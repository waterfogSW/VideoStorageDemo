package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoSaveMetadataPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoMultipartUploadService implements VideoMultipartUploadCommand {

  private static final String UPLOAD_FOLDER = "/app/data/";

  private final VideoSaveMetadataPort videoSaveMetadataPort;
  private final VideoFactory videoFactory;

  @Override
  public void upload(MultipartFile multipartFile) {
    final var video = videoFactory.create(UPLOAD_FOLDER, multipartFile);
    videoSaveMetadataPort.save(video);
  }

}

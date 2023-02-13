package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadRequest;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadResponse;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoSaveMetadataPort;
import com.shoplive.videostoragedemo.video.application.util.VideoFileFactory;
import com.shoplive.videostoragedemo.video.application.util.VideoFileResizer;
import com.shoplive.videostoragedemo.video.domain.Video;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoMultipartUploadService implements VideoMultipartUploadCommand {

  private final VideoStorageProperties videoStorageProperties;
  private final VideoSaveMetadataPort videoSaveMetadataPort;
  private final VideoFileFactory videoFileFactory;
  private final VideoFileResizer videoFileResizer;

  @Override
  @Transactional
  public VideoUploadResponse upload(
      MultipartFile multipartFile,
      VideoUploadRequest request
  ) {
    final var title = StringUtils.isBlank(request.title()) ? multipartFile.getOriginalFilename() : request.title();
    final var video = Video.from(title);

    final var originalFileInfo = videoFileFactory.create(videoStorageProperties.getPath(), multipartFile);
    video.setOriginalFile(originalFileInfo);

    final var savedVideo = videoSaveMetadataPort.save(video);
    videoFileResizer.resize(savedVideo, -1, 360);

    return new VideoUploadResponse(savedVideo.getId());
  }

}

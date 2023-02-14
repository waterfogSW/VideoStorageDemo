package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadRequest;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadResponse;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetadataSavePort;
import com.shoplive.videostoragedemo.video.application.util.VideoFileStorageUtil;
import com.shoplive.videostoragedemo.video.domain.Video;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoMultipartUploadService implements VideoMultipartUploadCommand {

  private final VideoMetadataSavePort videoMetadataSavePort;
  private final VideoFileStorageUtil videoFileStorageUtil;

  @Override
  @Transactional
  public VideoUploadResponse upload(
      MultipartFile multipartFile,
      VideoUploadRequest request
  ) {
    final var title = StringUtils.isBlank(request.title()) ? multipartFile.getOriginalFilename() : request.title();
    final var video = Video.from(title);

    final var originalFileInfo = videoFileStorageUtil.create(multipartFile);
    video.setOriginalFile(originalFileInfo);
    final var savedVideo = videoMetadataSavePort.save(video);

    videoFileStorageUtil.resize(savedVideo, -1, 360);

    return new VideoUploadResponse(savedVideo.getId());
  }

}

package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadRequest;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadResponse;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetadataSavePort;
import com.shoplive.videostoragedemo.video.application.util.VideoFileUtil;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoMultipartUploadService implements VideoMultipartUploadCommand {

  private final VideoMetadataSavePort videoMetadataSavePort;
  private final VideoFileUtil videoFileUtil;

  @Override
  @Transactional
  public VideoUploadResponse upload(
      MultipartFile multipartFile,
      VideoUploadRequest request
  ) {
    final var title = StringUtils.isBlank(request.title()) ? multipartFile.getOriginalFilename() : request.title();
    final var video = Video.from(title);

    final var originalFileInfo = videoFileUtil.create(multipartFile);
    video.setOriginalFile(originalFileInfo);
    final var savedVideo = videoMetadataSavePort.save(video);

    resizeVideoFile(savedVideo, originalFileInfo);

    return new VideoUploadResponse(savedVideo.getId());
  }

  private void resizeVideoFile(
      Video video,
      VideoFileInfo originalFileInfo
  ) {
    final var resizeCallBack = videoFileUtil.resize(originalFileInfo, -1, 360);
    resizeCallBack.thenAccept(resizedFileInfo -> {
      video.setResizedFile(resizedFileInfo);
      videoMetadataSavePort.save(video);
    });
  }

}

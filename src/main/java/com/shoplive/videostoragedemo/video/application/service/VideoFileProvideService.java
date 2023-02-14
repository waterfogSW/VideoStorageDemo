package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.application.port.in.VideoFileProvideCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetaDataLookUpPort;
import com.shoplive.videostoragedemo.video.application.util.VideoFileStorageUtil;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoFileProvideService implements VideoFileProvideCommand {

  private final VideoMetaDataLookUpPort videoMetaDataLookUpPort;
  private final VideoStorageProperties videoStorageProperties;
  private final VideoFileStorageUtil videoFileStorageUtil;

  @Override
  public VideoFileResource provide(String fileName) {
    final var video = videoMetaDataLookUpPort.lookUpByFileName(fileName);

    if (fileName.startsWith(videoStorageProperties.getResizePrefix())) {
      return videoFileStorageUtil.readByteArrayResourceByVideoInfo(video.getResized());
    }

    return videoFileStorageUtil.readByteArrayResourceByVideoInfo(video.getOriginal());
  }

}

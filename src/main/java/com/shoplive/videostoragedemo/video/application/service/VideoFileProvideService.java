package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.application.port.in.VideoFileProvideCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetaDataLookUpPort;
import com.shoplive.videostoragedemo.video.application.util.FileResourceUtil;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoFileProvideService implements VideoFileProvideCommand {

  private final VideoMetaDataLookUpPort videoMetaDataLookUpPort;
  private final VideoStorageProperties videoStorageProperties;
  private final FileResourceUtil fileResourceUtil;

  @Override
  public VideoFileResource provide(String fileName) {
    final var video = videoMetaDataLookUpPort.lookUpByFileName(fileName);

    if (fileName.startsWith(videoStorageProperties.getResizePrefix())) {
      return getVideoFileResource(video.getResized());
    }

    return getVideoFileResource(video.getOriginal());
  }

  private VideoFileResource getVideoFileResource(VideoFileInfo videoFileInfo) {
    final var path = videoFileInfo.filePath();
    final var videoFileName = path.getFileName();
    final var resource = fileResourceUtil.readByteArrayResourceFromPath(path);
    return new VideoFileResource(videoFileName.toString(), resource);
  }

}

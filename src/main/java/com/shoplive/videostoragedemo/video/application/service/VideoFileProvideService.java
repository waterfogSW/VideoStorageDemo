package com.shoplive.videostoragedemo.video.application.service;

import org.springframework.stereotype.Service;

import com.shoplive.videostoragedemo.video.application.port.in.VideoFileProvideCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoLookUpMetaDataPort;
import com.shoplive.videostoragedemo.video.application.util.FileResourceUtil;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoFileProvideService implements VideoFileProvideCommand {

  private final VideoLookUpMetaDataPort videoLookUpMetaDataPort;
  private final FileResourceUtil fileResourceUtil;

  @Override
  public VideoFileResource provide(String fileName) {
    final var video = videoLookUpMetaDataPort.lookUp(fileName);
    final var videoFilePath = video.getOriginal().filePath();

    final var videoFileName = videoFilePath.getFileName();
    final var resource = fileResourceUtil.readByteArrayResourceFromPath(videoFilePath);

    return new VideoFileResource(videoFileName.toString(), resource);
  }

}

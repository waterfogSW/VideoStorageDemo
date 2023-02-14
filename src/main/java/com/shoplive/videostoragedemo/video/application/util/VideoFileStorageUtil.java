package com.shoplive.videostoragedemo.video.application.util;

import java.nio.file.Files;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

@Component
public class VideoFileStorageUtil {

  public VideoFileResource readByteArrayResourceByVideoInfo(VideoFileInfo videoFileInfo) {

    final var path = videoFileInfo.filePath();
    final var videoFileName = path.getFileName();

    try {
      final var bytes = Files.readAllBytes(path);
      final var resource = new ByteArrayResource(bytes);
      return new VideoFileResource(videoFileName.toString(), resource);
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

}

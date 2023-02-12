package com.shoplive.videostoragedemo.video.application.service;

import java.nio.file.Files;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.shoplive.videostoragedemo.video.application.port.in.VideoFileProvideCommand;
import com.shoplive.videostoragedemo.video.application.port.out.VideoLookUpMetaDataPort;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoFileProvideService implements VideoFileProvideCommand {

  private final VideoLookUpMetaDataPort videoLookUpMetaDataPort;

  @Override
  public VideoFileResource provide(String fileName) {
    final var filePath = videoLookUpMetaDataPort.lookUp(fileName)
                                                .getFilePath();
    try {
      final var bytes = Files.readAllBytes(filePath);
      final var resource = new ByteArrayResource(bytes);

      return new VideoFileResource(filePath.getFileName().toString(), resource);
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }

  }

}

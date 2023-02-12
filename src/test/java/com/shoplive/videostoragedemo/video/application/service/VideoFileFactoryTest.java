package com.shoplive.videostoragedemo.video.application.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

@DisplayName("App - VideoFile 생성 기능")
class VideoFileFactoryTest {

  VideoFileFactory videoFileFactory = new VideoFileFactory();

  @Test
  @DisplayName("파일 생성")
  void createVideo() throws IOException {
    //setup
    final var tempDirectory = Files.createTempDirectory("temp" + UUID.randomUUID());

    //given
    final var fileName = "test";
    final var file = mockMultipartFile(fileName, "video/mp4", 1024 * 1024 * 100);

    //when
    final var videoFileInfo = videoFileFactory.create(tempDirectory.toString() + "/", file);

    //then
    final var filePath = videoFileInfo.filePath();
    Assertions.assertThat(filePath.endsWith("test")).isTrue();

    //tearDown
    FileUtils.deleteDirectory(tempDirectory.toFile());
  }

  private MockMultipartFile mockMultipartFile(
      String name,
      String fileFormat,
      int size
  ) {
    final var content = new byte[size];
    return new MockMultipartFile(name, name, fileFormat, content);
  }

}

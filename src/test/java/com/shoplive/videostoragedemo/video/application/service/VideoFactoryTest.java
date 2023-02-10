package com.shoplive.videostoragedemo.video.application.service;

import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

@DisplayName("VideoFactory - Video 생성요청")
class VideoFactoryTest {

  VideoFactory videoFactory = new VideoFactory();

  @Test
  void createVideo() throws IOException {
    //setup
    final var tempDirectory = Files.createTempDirectory("temp");

    //given
    final var fileName = "test";
    final var file = mockMultipartFile(fileName, "video/mp4", 1024 * 1024 * 100);

    //when
    final var video = videoFactory.create(tempDirectory.toString() + "/", file);

    //then
    final var filePath = video.getFilePath();
    Assertions.assertThat(filePath.endsWith("test"))
              .isTrue();

    //tearDown
    FileUtils.deleteDirectory(tempDirectory.toFile());
  }

  @Test
  @DisplayName("100MB 용량 초과시 예외 발생")
  void fileSizeException() {
    // given
    final var file = mockMultipartFile("test", "video/mp4", 1024 * 1024 * 100 + 1);

    // when, then
    Assertions.assertThatThrownBy(() -> videoFactory.create("test", file))
              .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("파일 형식이 video/mp4 가 아닐경우 예외 발생")
  void fileFormatException() {
    //given
    final var file = mockMultipartFile("test", "testFormat", 100);

    // when, then
    Assertions.assertThatThrownBy(() -> videoFactory.create("test", file))
              .isInstanceOf(IllegalArgumentException.class);
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

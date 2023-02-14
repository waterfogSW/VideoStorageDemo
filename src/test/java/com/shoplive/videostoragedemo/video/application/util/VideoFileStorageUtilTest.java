package com.shoplive.videostoragedemo.video.application.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;

@DisplayName("Util - 영상 파일 생성 기능")
@ExtendWith(MockitoExtension.class)
class VideoFileStorageUtilTest {

  @Mock
  VideoStorageProperties properties;

  @InjectMocks
  VideoFileStorageUtil storageUtil;

  @Test
  @DisplayName("영상 파일 생성")
  void createVideo() throws IOException {
    // file storage properties
    final var tempDirectoryPath = "";
    given(properties.getPath()).willReturn(tempDirectoryPath);

    // create a mock MultipartFile
    final var expectedMultipartName = "file";
    final var expectedFileName = "test.mp4";
    final var expectedContentType = "video/mp4";
    byte[] expectedFileContent = "test video content".getBytes();
    final var mockFile = new MockMultipartFile(
        expectedMultipartName,
        expectedFileName,
        expectedContentType,
        expectedFileContent
    );

    // call the create method
    final var fileInfo = storageUtil.create(mockFile);

    // assert that the file was created and has the correct content
    final var filePath = fileInfo.filePath();

    assertThat(Files.exists(filePath)).isTrue();
    assertThat(expectedFileContent.length).isEqualTo(Files.size(filePath));
    assertThat(expectedFileContent).isEqualTo(Files.readAllBytes(filePath));

    // assert that the filename is a UUID
    String filename = filePath.getFileName()
                              .toString();

    assertThat(filename).isEqualTo(expectedFileName);

    // delete the temporary file
    Files.deleteIfExists(filePath);
  }

}

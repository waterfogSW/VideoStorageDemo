package com.shoplive.videostoragedemo.video.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetaDataLookUpPort;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

@DisplayName("App - 메타데이터 관리 기능")
@ExtendWith(MockitoExtension.class)
class VideoMetadataServiceTest {

  @Mock
  private VideoMetaDataLookUpPort videoMetaDataLookUpPort;

  @Mock
  private VideoStorageProperties videoStorageProperties;

  @InjectMocks
  private VideoMetadataService videoMetadataService;

  @Test
  @DisplayName("id 값에 해당하는 영상 파일의 정보를 응답한다")
  void lookupDetail_shouldReturnCorrectResponse() {
    // Given
    final var expectedFileSize = 1024L;
    final var expectedFileName = "original.mp4";
    final var originalFileInfo = new VideoFileInfo(expectedFileSize, Path.of("resources/" + expectedFileName));
    final var video = new Video(1L, "Test video", originalFileInfo, null);
    given(videoMetaDataLookUpPort.lookUpById(1L)).willReturn(video);

    final var resourcePath = "http://localhost:8080/path/to/";
    given(videoStorageProperties.getResourceUrl()).willReturn(resourcePath);

    // When
    final var response = videoMetadataService.lookupDetail(1L);

    // Then
    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.title()).isEqualTo("Test video");

    final var fileSize = (Long)ReflectionTestUtils.getField(response.original(), "fileSize");
    final var videoUrl = (String)ReflectionTestUtils.getField(response.original(), "videoUrl");
    assertThat(fileSize).isEqualTo(expectedFileSize);
    assertThat(videoUrl).isEqualTo(resourcePath + expectedFileName);

  }

}

package com.shoplive.videostoragedemo.video.application.service;

import static org.mockito.BDDMockito.*;

import java.nio.file.Path;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetaDataLookUpPort;
import com.shoplive.videostoragedemo.video.application.util.VideoFileUtil;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

@DisplayName("App - 영상 파일 제공 기능")
@ExtendWith(MockitoExtension.class)
public class VideoFileProvideServiceTest {

  @Mock
  private VideoStorageProperties videoStorageProperties;

  @Mock
  private VideoMetaDataLookUpPort videoMetaDataLookUpPort;

  @Mock
  private VideoFileUtil videoFileUtil;

  @InjectMocks
  private VideoFileProvideService videoFileProvideService;

  @Test
  @DisplayName("FileName에 해당하는 비디오 파일을 제공한다.")
  void provide_returnsVideoFileResource() {
    // Given
    final var fileName = "video.mp4";
    final var expectedTitle = "Video Title";
    final var expectedFileSize = 1024L * 1024L * 10L;
    final var expectedFilePath = Path.of("path/to/video.mp4");
    final var expectedResource = new byte[]{1, 2, 3};
    final var expectedByteArrayResource = new ByteArrayResource(expectedResource);
    final var expectedVideoFileResource = new VideoFileResource(fileName, expectedByteArrayResource);

    final var expectedFileInfo = new VideoFileInfo(expectedFileSize, expectedFilePath);
    final var video = new Video(null, expectedTitle, expectedFileInfo, null);
    given(videoStorageProperties.getResizePrefix()).willReturn("resized_");
    given(videoMetaDataLookUpPort.lookUpByFileName(fileName)).willReturn(video);
    given(videoFileUtil.readByteArrayResourceByVideoInfo(any(VideoFileInfo.class)))
        .willReturn(expectedVideoFileResource);

    // When
    final var result = videoFileProvideService.provide(fileName);

    // Then
    Assertions.assertThat(result.fileName())
              .isEqualTo(fileName);
    Assertions.assertThat(result.resource())
              .isEqualTo(expectedByteArrayResource);
  }

}

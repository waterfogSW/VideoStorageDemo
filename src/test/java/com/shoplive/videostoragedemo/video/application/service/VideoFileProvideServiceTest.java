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

import com.shoplive.videostoragedemo.video.application.port.out.VideoLookUpMetaDataPort;
import com.shoplive.videostoragedemo.video.application.util.FileResourceUtil;
import com.shoplive.videostoragedemo.video.domain.Video;

@DisplayName("App - 비디오파일 제공 요청")
@ExtendWith(MockitoExtension.class)
public class VideoFileProvideServiceTest {

  @Mock
  private VideoLookUpMetaDataPort videoLookUpMetaDataPort;

  @Mock
  private FileResourceUtil fileResourceUtil;

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

    final var video = new Video(expectedTitle, expectedFileSize, expectedFilePath);
    given(videoLookUpMetaDataPort.lookUp(fileName)).willReturn(video);
    given(fileResourceUtil.readByteArrayResourceFromPath(expectedFilePath)).willReturn(expectedByteArrayResource);

    // When
    final var result = videoFileProvideService.provide(fileName);

    // Then
    Assertions.assertThat(result.fileName()).isEqualTo(fileName);
    Assertions.assertThat(result.resource()).isEqualTo(expectedByteArrayResource);
  }

}

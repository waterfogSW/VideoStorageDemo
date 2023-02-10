package com.shoplive.videostoragedemo.video.application.service;

import static org.mockito.BDDMockito.*;

import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.application.port.out.VideoSaveMetadataPort;
import com.shoplive.videostoragedemo.video.domain.Video;

@DisplayName("VideoMultipartUploadService - 업로드 요청")
@ExtendWith(MockitoExtension.class)
class VideoMultipartUploadServiceTest {

  @Mock
  VideoFactory videoFactory;

  @Mock
  VideoSaveMetadataPort videoSaveMetadataPort;

  @InjectMocks
  VideoMultipartUploadService videoMultipartUploadService;

  @Test
  @DisplayName("Video 생성요청후, 메타데이터 저장을 요청")
  void requestCreateVideo() {
    //given
    final var mockMultipartFile = mockMultipartfile();
    given(videoFactory.create(anyString(), any(MultipartFile.class))).willReturn(mockVideo());

    //when
    videoMultipartUploadService.upload(mockMultipartFile);

    //then
    verify(videoSaveMetadataPort).save(any(Video.class));
  }

  private MockMultipartFile mockMultipartfile() {
    return new MockMultipartFile("test", "test", "video/mp4", new byte[]{});
  }

  private Video mockVideo() {
    return new Video("test", 100, Path.of("test"));
  }

}

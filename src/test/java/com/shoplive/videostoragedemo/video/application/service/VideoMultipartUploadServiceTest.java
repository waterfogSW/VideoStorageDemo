package com.shoplive.videostoragedemo.video.application.service;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadRequest;
import com.shoplive.videostoragedemo.video.application.port.out.VideoSaveMetadataPort;
import com.shoplive.videostoragedemo.video.application.util.VideoFileFactory;
import com.shoplive.videostoragedemo.video.application.util.VideoFileResizer;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

@DisplayName("App - 영상 파일 업로드 기능")
@ExtendWith(MockitoExtension.class)
class VideoMultipartUploadServiceTest {


  @Mock
  VideoFileFactory videoFileFactory;

  @Mock
  VideoSaveMetadataPort videoSaveMetadataPort;

  @Mock
  VideoFileResizer videoFileResizer;

  @InjectMocks
  VideoMultipartUploadService videoMultipartUploadService;

  @Test
  @DisplayName("Video 생성요청후, 메타데이터 저장을 요청")
  void requestCreateVideo() {
    //given
    final var request = new VideoUploadRequest("test");
    final var mockMultipartFile = mockMultipartfile();
    given(videoFileFactory.create(any(MultipartFile.class))).willReturn(mockVideoFileInfo());
    given(videoSaveMetadataPort.save(any(Video.class))).willReturn(mockSavedVideo());

    //when
    videoMultipartUploadService.upload(mockMultipartFile, request);

    //then
    verify(videoSaveMetadataPort).save(any(Video.class));
    verify(videoFileResizer).resize(any(Video.class), anyInt(), anyInt());
  }

  private MockMultipartFile mockMultipartfile() {
    return new MockMultipartFile("test", "test", "video/mp4", new byte[]{});
  }

  private VideoFileInfo mockVideoFileInfo() {
    return new VideoFileInfo(100L, null);
  }

  private Video mockSavedVideo() {
    return new Video(1L, "title", mockVideoFileInfo(), null);
  }

}

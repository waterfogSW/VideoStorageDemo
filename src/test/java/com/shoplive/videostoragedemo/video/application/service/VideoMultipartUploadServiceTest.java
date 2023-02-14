package com.shoplive.videostoragedemo.video.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadRequest;
import com.shoplive.videostoragedemo.video.application.port.out.VideoMetadataSavePort;
import com.shoplive.videostoragedemo.video.application.util.VideoFileUtil;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

@DisplayName("App - 영상 파일 업로드 기능")
@ExtendWith(MockitoExtension.class)
public class VideoMultipartUploadServiceTest {

  @Mock
  private VideoMetadataSavePort videoMetadataSavePort;

  @Mock
  private VideoFileUtil videoFileUtil;

  @InjectMocks
  private VideoMultipartUploadService videoMultipartUploadService;

  @Test
  @DisplayName("영상 파일 요청 -> 영상 변환요청(비동기) -> 영상 id값 반환")
  void testUpload() throws Exception {
    // Given
    final var videoId = 12345L;
    final var title = "Test Video";
    final var multipartFile = new MockMultipartFile("file", new byte[]{});
    final var request = new VideoUploadRequest(title);

    final var originalFileInfo = VideoFileInfo.from(Paths.get("test.mp4"));
    final var resizedFileInfo = VideoFileInfo.from(Paths.get("test_resized.mp4"));

    final var savedVideo = new Video(videoId, title, originalFileInfo, null);

    when(videoFileUtil.create(multipartFile)).thenReturn(originalFileInfo);
    when(videoMetadataSavePort.save(any(Video.class))).thenReturn(savedVideo);

    // Asynchronously return the resized file info
    CompletableFuture<VideoFileInfo> resizeFuture = new CompletableFuture<>();
    resizeFuture.complete(resizedFileInfo);
    when(videoFileUtil.resize(originalFileInfo, -1, 360)).thenReturn(resizeFuture);

    // When
    final var response = videoMultipartUploadService.upload(multipartFile, request);

    // Then
    assertThat(response.id()).isEqualTo(videoId);

    verify(videoMetadataSavePort).save(savedVideo);
    verify(videoFileUtil).resize(originalFileInfo, -1, 360);

    // Wait for the asynchronous resize operation to complete
    VideoFileInfo result = resizeFuture.get();
    assertThat(savedVideo.getResized()).isNotNull()
                                       .isEqualTo(resizedFileInfo);
  }

}

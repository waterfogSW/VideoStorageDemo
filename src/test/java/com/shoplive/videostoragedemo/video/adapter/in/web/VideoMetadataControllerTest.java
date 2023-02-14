package com.shoplive.videostoragedemo.video.adapter.in.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoMetadataLookupDetailResponse;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMetadataLookupCommand;
import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

@DisplayName("Web - 영상 정보 조회 기능")
@WebMvcTest(VideoMetadataController.class)
@AutoConfigureRestDocs
class VideoMetadataControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private VideoMetadataLookupCommand videoMetadataLookupCommand;

  @Test
  @DisplayName("GET: /video/{id} 요청 성공시 200응답")
  void testLookupDetail() throws Exception {
    long id = 1L;

    final var originalVideoFileInfo = new VideoFileInfo(1000L, Path.of("original.mp4"));
    final var resizedVideoFileInfo = new VideoFileInfo(500L, Path.of("resized.mp4"));
    final var createLocalDateTime = LocalDateTime.now();
    final var video = new Video(id, "test video", originalVideoFileInfo, resizedVideoFileInfo, createLocalDateTime);

    final var resourceUrl = "http://localhost:8080/path/to/";
    final var response = VideoMetadataLookupDetailResponse.of(video, resourceUrl);
    given(videoMetadataLookupCommand.lookupDetail(id)).willReturn(response);

    mockMvc.perform(get("/video/{id}", id))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(id))
           .andExpect(jsonPath("$.title").value("test video"))
           .andExpect(jsonPath("$.original.fileSize").value(1000L))
           .andExpect(jsonPath("$.original.videoUrl").value(resourceUrl + originalVideoFileInfo.filePath()))
           .andExpect(jsonPath("$.resized.fileSize").value(500L))
           .andExpect(jsonPath("$.resized.videoUrl").value(resourceUrl + resizedVideoFileInfo.filePath()))
           .andExpect(jsonPath("$.createAt").value(createLocalDateTime.toString()))
           .andDo(document(
               "Video information lookup",
               pathParameters(
                   parameterWithName("id").description("The ID of the video to retrieve")
               ),
               responseFields(
                   fieldWithPath("id").description("The ID of the video"),
                   fieldWithPath("title").description("The title of the video"),
                   fieldWithPath("original.fileSize").description("The file size of the original video"),
                   fieldWithPath("original.videoUrl").description("The URL of the original video file"),
                   fieldWithPath("resized.fileSize").description("The file size of the resized video"),
                   fieldWithPath("resized.videoUrl").description("The URL of the resized video file"),
                   fieldWithPath("createAt").description("The creation date of the video")
               )
           ));
  }

}

package com.shoplive.videostoragedemo.video.adapter.in.web;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadRequest;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadResponse;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;

@DisplayName("Web - 영상 파일 업로드 기능")
@AutoConfigureRestDocs
@WebMvcTest(controllers = VideoMultipartUploadController.class)
class VideoMultipartUploadControllerTest {

  private static final String TARGET_API = "/video/upload";

  @MockBean
  VideoMultipartUploadCommand uploadCommand;

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("POST: /video/upload 제목포함 요청 성공시 201응답")
  void uploadWithTitle() throws Exception {
    // given
    final var multipartFormName = "file";
    final var fileName = "video.mp4";
    final var byteSize = 10;
    final var mediaType = MediaType.MULTIPART_FORM_DATA_VALUE;
    final var file = mockMultipartFile(multipartFormName, fileName, byteSize, mediaType);
    final var requestBody = new VideoUploadRequest("test title");
    final var request = multipart(TARGET_API).file(file)
                                             .param("title", requestBody.title());

    final var expectedVideoId = 1L;
    given(uploadCommand.upload(any(MultipartFile.class), any(VideoUploadRequest.class)))
        .willReturn(mockVideoUploadResponse(expectedVideoId));

    // when, then
    mockMvc.perform(request)
           .andExpect(status().isCreated())
           .andDo(
               document(
                   "Video multipart upload",
                   requestParts(
                       partWithName("file").description("Video file to upload"),
                       partWithName("title").description("Video title")
                                            .optional()
                   )
               )
           );
  }

  private VideoUploadResponse mockVideoUploadResponse(long id) {
    return new VideoUploadResponse(id);
  }

  private MockMultipartFile mockMultipartFile(
      String name,
      String fileName,
      int byteSize,
      String mediaType
  ) {
    var fileData = new byte[byteSize * 1024 * 1024];
    return new MockMultipartFile(name, fileName, mediaType, fileData);
  }

}

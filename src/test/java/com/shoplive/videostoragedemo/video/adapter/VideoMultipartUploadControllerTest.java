package com.shoplive.videostoragedemo.video.adapter;

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

import com.shoplive.videostoragedemo.video.adapter.in.web.VideoMultipartUploadController;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;

@DisplayName("멀티 파트 파일 POST 요청 기능")
@AutoConfigureRestDocs
@WebMvcTest(controllers = VideoMultipartUploadController.class)
class VideoMultipartUploadControllerTest {

  private static final String TARGET_API = "/video/upload";

  @MockBean
  VideoMultipartUploadCommand uploadCommand;

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("멀티파트 폼으로 요청 성공시 200을 응답한다")
  void upload() throws Exception {

    final var file =
        generateMockMultipartFile("file", "video.mp4", 10 * 1024 * 1024, MediaType.MULTIPART_FORM_DATA_VALUE);

    mockMvc.perform(multipart(TARGET_API).file(file))
           .andExpect(status().isOk())
           .andDo(
               document(
                   "Video multipart upload",
                   requestParts(partWithName("file").description("Video file to upload"))
               )
           );
  }

  private MockMultipartFile generateMockMultipartFile(
      String name,
      String fileName,
      int byteSize,
      String mediaType
  ) {
    var fileData = new byte[byteSize * 1024 * 1024];
    return new MockMultipartFile("file", fileName, mediaType, fileData);
  }

}

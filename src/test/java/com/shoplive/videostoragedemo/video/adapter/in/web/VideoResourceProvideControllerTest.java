package com.shoplive.videostoragedemo.video.adapter.in.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.shoplive.videostoragedemo.video.application.port.in.VideoFileProvideCommand;
import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

@DisplayName("VideoResourceProvideController - 리소스 GET 요청")
@AutoConfigureRestDocs
@WebMvcTest(controllers = VideoResourceProvideController.class)
class VideoResourceProvideControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private VideoFileProvideCommand fileProvideCommand;

  @Test
  @DisplayName("fileName에 해당하는 리소스를 응답한다.")
  void provide_shouldReturnVideoFile() throws Exception {
    // Given
    final var fileName = "example.mp4";
    final var content = new byte[]{0x01, 0x02, 0x03};
    final var fileResource = new VideoFileResource(fileName, new ByteArrayResource(content));
    given(fileProvideCommand.provide(fileName)).willReturn(fileResource);

    // When, Then
    mockMvc.perform(get("/path/to/{fileName}", fileName))
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.parseMediaType("video/mp4")))
           .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName))
           .andExpect(content().bytes(content))
           .andDo(document(
               "video-provide",
               preprocessResponse(prettyPrint()),
               pathParameters(parameterWithName("fileName").description("The name of the video file")),
               responseHeaders(
                   headerWithName(HttpHeaders.CONTENT_DISPOSITION).description("The content disposition header"),
                   headerWithName(HttpHeaders.CONTENT_TYPE).description("The content type header"),
                   headerWithName(HttpHeaders.CONTENT_LENGTH).description("The content length header")
               )
           ));
  }

}

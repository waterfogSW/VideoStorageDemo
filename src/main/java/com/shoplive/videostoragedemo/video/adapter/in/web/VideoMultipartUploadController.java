package com.shoplive.videostoragedemo.video.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VideoMultipartUploadController {

  private final VideoMultipartUploadCommand videoMultipartUploadCommand;

  @PostMapping(value = "video/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void upload(
      @RequestParam
      MultipartFile file
  ) {
    videoMultipartUploadCommand.upload(file);
  }

}

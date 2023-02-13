package com.shoplive.videostoragedemo.video.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadRequest;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadResponse;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMultipartUploadCommand;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VideoMultipartUploadController {

  private final VideoMultipartUploadCommand videoMultipartUploadCommand;

  @PostMapping(value = "video/upload")
  @ResponseStatus(HttpStatus.CREATED)
  public VideoUploadResponse upload(
      @RequestParam
      MultipartFile file,
      VideoUploadRequest request
  ) {
    return videoMultipartUploadCommand.upload(file, request);
  }

}

package com.shoplive.videostoragedemo.video.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadRequest;
import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoUploadResponse;

public interface VideoMultipartUploadCommand {

  VideoUploadResponse upload(MultipartFile file, VideoUploadRequest request);

}

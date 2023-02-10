package com.shoplive.videostoragedemo.video.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface VideoMultipartUploadCommand {

  void upload(MultipartFile file);

}

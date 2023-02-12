package com.shoplive.videostoragedemo.video.adapter.in.web;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoplive.videostoragedemo.video.application.port.in.VideoFileProvideCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("path/to")
public class VideoResourceProvideController {

  private final VideoFileProvideCommand fileProvideCommand;

  @GetMapping(value = "{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<ByteArrayResource> provide(@PathVariable String fileName) {
    final var fileResource = fileProvideCommand.provide(fileName);
    final var resource = fileResource.resource();

    return ResponseEntity.ok()
                         .contentType(MediaType.parseMediaType("video/mp4"))
                         .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileResource.fileName())
                         .contentLength(resource.contentLength())
                         .body(resource);
  }

}

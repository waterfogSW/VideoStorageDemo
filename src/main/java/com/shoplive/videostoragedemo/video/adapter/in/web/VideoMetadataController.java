package com.shoplive.videostoragedemo.video.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoMetadataLookupDetailResponse;
import com.shoplive.videostoragedemo.video.application.port.in.VideoMetadataLookupCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VideoMetadataController {

  private final VideoMetadataLookupCommand videoMetadataLookupCommand;

  @GetMapping("video/{id}")
  public VideoMetadataLookupDetailResponse lookupDetail(@PathVariable long id) {
    return videoMetadataLookupCommand.lookupDetail(id);
  }

}

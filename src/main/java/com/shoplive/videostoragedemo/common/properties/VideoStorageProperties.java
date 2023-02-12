package com.shoplive.videostoragedemo.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "video.storage")
public class VideoStorageProperties {

  private final String path;

  @ConstructorBinding
  public VideoStorageProperties(String path) {
    this.path = path.endsWith("/") ? path : path + "/";
  }

}

package com.shoplive.videostoragedemo.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "video.storage")
public class VideoStorageProperties {

  private final String path;
  private final String resizePrefix;
  private final String resourceUrl;

  @ConstructorBinding
  public VideoStorageProperties(
      String path,
      String resizePrefix,
      String resourceUrl
  ) {
    this.path = path.endsWith("/") ? path : path + "/";
    this.resizePrefix = resizePrefix.endsWith("_") ? resizePrefix : resizePrefix + "_";
    this.resourceUrl = resourceUrl.endsWith("/") ? resourceUrl : resourceUrl + "/";
  }

}

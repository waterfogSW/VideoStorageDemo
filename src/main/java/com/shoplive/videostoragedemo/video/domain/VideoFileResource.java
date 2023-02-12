package com.shoplive.videostoragedemo.video.domain;

import org.springframework.core.io.ByteArrayResource;

public record VideoFileResource(
    String fileName,
    ByteArrayResource resource
) {

}

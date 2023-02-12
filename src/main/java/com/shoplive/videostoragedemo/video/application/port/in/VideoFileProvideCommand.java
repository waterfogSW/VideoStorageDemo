package com.shoplive.videostoragedemo.video.application.port.in;

import com.shoplive.videostoragedemo.video.domain.VideoFileResource;

public interface VideoFileProvideCommand {

  VideoFileResource provide(String fileName);

}

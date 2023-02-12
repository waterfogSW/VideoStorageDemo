package com.shoplive.videostoragedemo.video.application.port.out;

import com.shoplive.videostoragedemo.video.domain.Video;

public interface VideoSaveMetadataPort {

  Video save(Video video);

}

package com.shoplive.videostoragedemo.video.application.port.out;

import com.shoplive.videostoragedemo.video.domain.Video;

public interface VideoMetaDataLookUpPort {

  Video lookUpByFileName(String title);

  Video lookUpById(long id);

}

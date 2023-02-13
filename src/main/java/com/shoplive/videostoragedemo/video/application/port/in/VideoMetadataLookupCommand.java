package com.shoplive.videostoragedemo.video.application.port.in;

import com.shoplive.videostoragedemo.video.adapter.in.web.dto.VideoMetadataLookupDetailResponse;

public interface VideoMetadataLookupCommand {

  VideoMetadataLookupDetailResponse lookupDetail(long id);

}

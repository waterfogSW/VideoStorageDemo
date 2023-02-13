package com.shoplive.videostoragedemo.video.adapter.in.web.dto;

import com.shoplive.videostoragedemo.video.domain.Video;
import com.shoplive.videostoragedemo.video.domain.VideoFileInfo;

public record VideoMetadataLookupDetailResponse(
    long id,
    String title,
    VideoFileInfoResponse original,
    VideoFileInfoResponse resized,
    String createAt
) {

  record VideoFileInfoResponse(
      long fileSize,
      String videoUrl
  ) {

    static VideoFileInfoResponse of(
        VideoFileInfo videoFileInfo,
        String resourceUrl
    ) {
      if (videoFileInfo == null) {
        return null;
      }
      return new VideoFileInfoResponse(videoFileInfo.fileSize(), resourceUrl + videoFileInfo.filePath()
                                                                                            .getFileName());
    }

  }

  public static VideoMetadataLookupDetailResponse of(
      Video video,
      String resourceUrl
  ) {
    return new VideoMetadataLookupDetailResponse(
        video.getId(),
        video.getTitle(),
        VideoFileInfoResponse.of(video.getOriginal(), resourceUrl),
        VideoFileInfoResponse.of(video.getResized(), resourceUrl),
        video.getTitle()
    );
  }

}

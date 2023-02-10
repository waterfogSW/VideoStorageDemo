package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoJpaRepository extends JpaRepository<VideoJpaEntity, Long> {

}

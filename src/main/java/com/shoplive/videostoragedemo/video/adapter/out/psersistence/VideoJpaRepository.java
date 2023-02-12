package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoJpaRepository extends JpaRepository<VideoJpaEntity, Long> {

  Optional<VideoJpaEntity> findByTitle(String title);

}

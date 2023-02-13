package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoplive.videostoragedemo.video.adapter.out.psersistence.entity.VideoJpaEntity;

public interface VideoJpaRepository extends JpaRepository<VideoJpaEntity, Long> {

  Optional<VideoJpaEntity> findByResized_FilePath(String path);

  Optional<VideoJpaEntity> findByOriginal_FilePath(String path);

}

package com.shoplive.videostoragedemo.video.adapter.out.psersistence;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoJpaEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(updatable = false, nullable = false)
  private String title;

  @Column(updatable = false, nullable = false)
  private long fileSize;

  @Column(updatable = false, nullable = false)
  private String filePath;

  @Column(updatable = false, nullable = false)
  private String createdAt;

  @Builder
  public VideoJpaEntity(
      Long id,
      String title,
      long fileSize,
      String filePath,
      String createdAt
  ) {
    this.id = id;
    this.title = title;
    this.fileSize = fileSize;
    this.filePath = filePath;
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    VideoJpaEntity that = (VideoJpaEntity)o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}

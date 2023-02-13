package com.shoplive.videostoragedemo.video.adapter.out.psersistence.entity;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
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

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "fileSize", column = @Column(name = "ORIGINAL_FILESIZE")),
      @AttributeOverride(name = "filePath", column = @Column(name = "ORIGINAL_FILEPATH")),
  })
  private VideoFileInfoJpaEntity original;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "fileSize", column = @Column(name = "RESIZED_FILESIZE")),
      @AttributeOverride(name = "filePath", column = @Column(name = "RESIZED_FILEPATH")),
  })
  private VideoFileInfoJpaEntity resized;

  @Column(updatable = false, nullable = false)
  private String createdAt;

  public VideoJpaEntity(
      Long id,
      String title,
      VideoFileInfoJpaEntity original,
      VideoFileInfoJpaEntity resized,
      String createdAt
  ) {
    this.id = id;
    this.title = title;
    this.original = original;
    this.resized = resized;
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

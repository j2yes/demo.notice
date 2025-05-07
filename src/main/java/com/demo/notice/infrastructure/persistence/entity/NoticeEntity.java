package com.demo.notice.infrastructure.persistence.entity;

import com.demo.notice.infrastructure.persistence.converter.UUIDToBytesConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "notices",
    indexes = @Index(name = "idx_notice_created_at_desc", columnList = "created_at DESC"))
public class NoticeEntity {

  @Id
  @Convert(converter = UUIDToBytesConverter.class)
  @Column(nullable = false, columnDefinition = "BINARY(16)")
  private UUID id;
  @Column(nullable = false, length = 255)
  private String title;
  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;
  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;
  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;
  @Column(name = "view_count", nullable = false)
  private Long viewCount;
  @Column(name = "has_attachment", nullable = false)
  private Boolean hasAttachment;
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Builder
  public NoticeEntity(UUID id, String title, String content, LocalDateTime startAt,
      LocalDateTime endAt, Long viewCount, Boolean hasAttachment, LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.startAt = startAt;
    this.endAt = endAt;
    this.viewCount = viewCount;
    this.hasAttachment = hasAttachment;
    this.createdAt = createdAt;
  }
}

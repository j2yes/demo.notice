package com.demo.notice.infrastructure.persistence.entity;

import com.demo.notice.infrastructure.persistence.converter.UUIDToBytesConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Table(name = "attachments",
    indexes = @Index(name = "idx_attachment_notice_id", columnList = "notice_id"))
public class AttachmentEntity {

  @Id
  @Convert(converter = UUIDToBytesConverter.class)
  @Column(nullable = false, columnDefinition = "BINARY(16)")
  private UUID id;
  @Column(name = "file_name", nullable = false)
  private String fileName;
  @Column(name = "file_size", nullable = false)
  private long fileSize;
  @Column(name = "content_type", nullable = false)
  private String contentType;
  @Column(name = "full_path", nullable = false)
  private String fullPath;

  @Convert(converter = UUIDToBytesConverter.class)
  @Column(name = "notice_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID noticeId;

  @Builder
  public AttachmentEntity(UUID id, String fileName, long fileSize, String contentType,
      String fullPath, UUID noticeId) {
    this.id = id;
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.contentType = contentType;
    this.fullPath = fullPath;
    this.noticeId = noticeId;
  }
}
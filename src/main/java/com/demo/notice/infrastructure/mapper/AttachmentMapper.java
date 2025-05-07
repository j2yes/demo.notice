package com.demo.notice.infrastructure.mapper;

import com.demo.notice.domain.model.Attachment;
import com.demo.notice.infrastructure.persistence.entity.AttachmentEntity;

public class AttachmentMapper {

  public static AttachmentEntity toEntity(Attachment domain) {
    return AttachmentEntity.builder()
        .id(domain.getId())
        .fileName(domain.getFileName())
        .fileSize(domain.getFileSize())
        .contentType(domain.getContentType())
        .fullPath(domain.getFullPath())
        .noticeId(domain.getNoticeId())
        .build();
  }

  public static Attachment toDomain(AttachmentEntity entity) {
    return Attachment.builder()
        .id(entity.getId())
        .fileName(entity.getFileName())
        .fileSize(entity.getFileSize())
        .contentType(entity.getContentType())
        .fullPath(entity.getFullPath())
        .noticeId(entity.getNoticeId())
        .build();
  }
}
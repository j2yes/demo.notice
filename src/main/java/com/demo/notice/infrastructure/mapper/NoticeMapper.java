package com.demo.notice.infrastructure.mapper;

import com.demo.notice.domain.model.Notice;
import com.demo.notice.infrastructure.persistence.entity.NoticeEntity;

public class NoticeMapper {

  public static NoticeEntity toEntity(Notice domain) {
    return NoticeEntity.builder()
        .id(domain.getId())
        .title(domain.getTitle())
        .content(domain.getContent())
        .startAt(domain.getStartAt())
        .endAt(domain.getEndAt())
        .viewCount(domain.getViewCount())
        .createdAt(domain.getCreatedAt())
        .hasAttachment(domain.isHasAttachment())
        .build();
  }

  public static Notice toDomain(NoticeEntity entity) {
    return Notice.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .content(entity.getContent())
        .startAt(entity.getStartAt())
        .endAt(entity.getEndAt())
        .viewCount(entity.getViewCount())
        .createdAt(entity.getCreatedAt())
        .hasAttachment(entity.getHasAttachment())
        .build();
  }
}
package com.demo.notice.domain.model;

import com.demo.notice.application.command.CreateNoticeCommand;
import com.demo.notice.application.command.UpdateNoticeCommand;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Notice {

  private final UUID id;
  private String title;
  private String content;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private long viewCount;
  private LocalDateTime createdAt;
  private boolean hasAttachment;
  private List<Attachment> attachments;

  @Builder
  public Notice(UUID id, String title, String content, LocalDateTime startAt, LocalDateTime endAt,
      long viewCount, LocalDateTime createdAt, boolean hasAttachment, List<Attachment> attachments) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.startAt = startAt;
    this.endAt = endAt;
    this.viewCount = viewCount;
    this.createdAt = createdAt;
    this.hasAttachment = hasAttachment;
    this.attachments = attachments;
  }

  public static Notice build(CreateNoticeCommand command) {
    boolean hasAttachment = !command.getNewFiles().isEmpty();
    return Notice.builder()
        .id(UUID.randomUUID())
        .title(command.getTitle())
        .content(command.getContent())
        .startAt(command.getStartAt())
        .endAt(command.getEndAt())
        .viewCount(0L)
        .createdAt(LocalDateTime.now())
        .hasAttachment(hasAttachment)
        .build();
  }

  public void update(UpdateNoticeCommand command) {
    this.title = command.getTitle();
    this.content = command.getContent();
    this.startAt = command.getStartAt();
    this.endAt = command.getEndAt();
  }

  public void resetViewCount(long viewCount) {
    this.viewCount = viewCount;
  }

  public void composeAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }
}

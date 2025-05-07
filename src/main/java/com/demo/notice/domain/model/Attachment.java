package com.demo.notice.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class Attachment {

  private final UUID id;
  private final String fileName;
  private final long fileSize;
  private final String contentType;
  @JsonIgnore
  private final String fullPath;
  private final UUID noticeId;

  @Builder(toBuilder = true)
  public Attachment(UUID id, String fileName, long fileSize, String contentType, String fullPath,
      UUID noticeId) {
    this.id = id;
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.contentType = contentType;
    this.fullPath = fullPath;
    this.noticeId = noticeId;
  }

  public static Attachment build(MultipartFile file, String fullPath, UUID noticeId) {
    return Attachment.builder()
        .id(UUID.randomUUID())
        .fileName(file.getOriginalFilename())
        .fileSize(file.getSize())
        .contentType(file.getContentType())
        .fullPath(fullPath)
        .noticeId(noticeId)
        .build();
  }
}

package com.demo.notice.infrastructure.adapter.persistence;

import com.demo.notice.application.port.persistence.AttachmentPersistencePort;
import com.demo.notice.domain.model.Attachment;
import com.demo.notice.domain.model.Notice;
import com.demo.notice.infrastructure.mapper.AttachmentMapper;
import com.demo.notice.infrastructure.persistence.entity.AttachmentEntity;
import com.demo.notice.infrastructure.persistence.repository.AttachmentRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttachmentPersistenceAdapter implements AttachmentPersistencePort {

  private final AttachmentRepository attachmentRepository;

  @Override
  public void saveAll(List<Attachment> attachments) {
    List<AttachmentEntity> entities = attachments.stream()
        .map(AttachmentMapper::toEntity).toList();
    attachmentRepository.saveAll(entities);
  }

  @Override
  public List<Attachment> findAllByNotice(Notice notice) {
    List<AttachmentEntity> attachmentEntities = attachmentRepository
        .findAllByNoticeId(notice.getId());
    return attachmentEntities.stream()
        .map(AttachmentMapper::toDomain).toList();
  }

  @Override
  public void delete(UUID id) {
    attachmentRepository.deleteById(id);
  }

  @Override
  public void deleteAllByNoticeId(UUID noticeId) {
    attachmentRepository.deleteAllByNoticeId(noticeId);
  }
}

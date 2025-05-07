package com.demo.notice.infrastructure.persistence.repository;

import com.demo.notice.infrastructure.persistence.entity.AttachmentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, UUID> {

  List<AttachmentEntity> findAllByNoticeId(UUID noticeId);

  void deleteAllByNoticeId(UUID noticeId);
}

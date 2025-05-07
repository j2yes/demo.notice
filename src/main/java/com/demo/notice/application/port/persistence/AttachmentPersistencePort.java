package com.demo.notice.application.port.persistence;

import com.demo.notice.domain.model.Attachment;
import com.demo.notice.domain.model.Notice;
import java.util.List;
import java.util.UUID;

public interface AttachmentPersistencePort {

  void saveAll(List<Attachment> attachments);

  List<Attachment> findAllByNotice(Notice notice);

  void delete(UUID id);

  void deleteAllByNoticeId(UUID id);
}

package com.demo.notice.application.service;

import com.demo.notice.application.command.CreateNoticeCommand;
import com.demo.notice.application.command.UpdateNoticeCommand;
import com.demo.notice.application.port.cache.CountCachePort;
import com.demo.notice.application.port.file.FileStoragePort;
import com.demo.notice.application.port.persistence.AttachmentPersistencePort;
import com.demo.notice.application.port.persistence.NoticePersistencePort;
import com.demo.notice.application.query.SearchNoticeQuery;
import com.demo.notice.application.usecase.CreateNoticeUseCase;
import com.demo.notice.application.usecase.DeleteNoticeUseCase;
import com.demo.notice.application.usecase.FindNoticeUseCase;
import com.demo.notice.application.usecase.UpdateNoticeUseCase;
import com.demo.notice.domain.model.Attachment;
import com.demo.notice.domain.model.Notice;
import com.demo.notice.global.exception.ResourceNotFoundException;
import static com.demo.notice.global.exception.ResourceNotFoundException.ResourceNotFoundExceptionCode.NOTICE_NOT_FOUND;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService implements CreateNoticeUseCase, UpdateNoticeUseCase, FindNoticeUseCase,
    DeleteNoticeUseCase {

  public static final String NOTICE_VIEW_COUNT_CACHE_PREFIX = "notice:views:";
  private final NoticePersistencePort noticePersistencePort;
  private final AttachmentPersistencePort attachmentPersistencePort;
  private final CountCachePort countCachePort;
  private final FileStoragePort fileStoragePort;

  @Override
  @Transactional
  public Notice create(CreateNoticeCommand command) {
    Notice notice = Notice.build(command);
    noticePersistencePort.save(notice);

    List<Attachment> attachments = new ArrayList<>();
    command.getNewFiles().forEach(file -> {
      String fullPath = fileStoragePort.store(file);
      Attachment attachment = Attachment.build(file, fullPath, notice.getId());
      attachments.add(attachment);
    });
    attachmentPersistencePort.saveAll(attachments);
    notice.composeAttachments(attachments);

    return notice;
  }

  @Override
  @Transactional
  public Notice update(UUID id, UpdateNoticeCommand command) {
    Notice notice = noticePersistencePort.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(NOTICE_NOT_FOUND));
    notice.update(command);
    noticePersistencePort.save(notice);

    List<Attachment> attachments = new ArrayList<>();
    command.getNewFiles().forEach(file -> {
      String fullPath = fileStoragePort.store(file);
      Attachment attachment = Attachment.build(file, fullPath, notice.getId());
      attachments.add(attachment);
    });
    attachmentPersistencePort.saveAll(attachments);
    List<Attachment> allAttachments = attachmentPersistencePort.findAllByNotice(notice);
    notice.composeAttachments(allAttachments);

    long viewCount = countCachePort.get(NOTICE_VIEW_COUNT_CACHE_PREFIX, id.toString());
    notice.resetViewCount(viewCount);

    return notice;
  }

  @Override
  public Page<Notice> list(SearchNoticeQuery query) {
    Page<Notice> page = noticePersistencePort.search(query);

    if (!page.isEmpty()) {
      List<String> ids = page.getContent().stream().map(Notice::getId).map(UUID::toString).toList();
      Map<String, Long> counts = countCachePort.multipleGet(NOTICE_VIEW_COUNT_CACHE_PREFIX, ids);

      page.getContent().forEach(notice -> {
        Long viewCount = counts.get(NOTICE_VIEW_COUNT_CACHE_PREFIX + notice.getId().toString());
        notice.resetViewCount(viewCount == null ? 0 : viewCount);
      });
    }

    return page;
  }

  @Override
  public Notice detail(UUID id) {
    Notice notice = noticePersistencePort.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(NOTICE_NOT_FOUND));
    long viewCount = countCachePort.incrementAndGet(NOTICE_VIEW_COUNT_CACHE_PREFIX, id.toString());
    notice.resetViewCount(viewCount + 1);

    List<Attachment> attachments = attachmentPersistencePort.findAllByNotice(notice);
    notice.composeAttachments(attachments);

    return notice;
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    attachmentPersistencePort.deleteAllByNoticeId(id);
    noticePersistencePort.delete(id);
  }
}

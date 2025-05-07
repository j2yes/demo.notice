package com.demo.notice.infrastructure.adapter.persistence;

import com.demo.notice.application.port.persistence.NoticePersistencePort;
import com.demo.notice.application.query.SearchNoticeQuery;
import com.demo.notice.domain.model.Notice;
import com.demo.notice.infrastructure.mapper.NoticeMapper;
import com.demo.notice.infrastructure.persistence.entity.NoticeEntity;
import com.demo.notice.infrastructure.persistence.repository.NoticeRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticePersistenceAdapter implements NoticePersistencePort {

  private final NoticeRepository noticeRepository;

  @Override
  public Notice save(Notice notice) {
    NoticeEntity entity = NoticeMapper.toEntity(notice);
    noticeRepository.save(entity);
    return NoticeMapper.toDomain(entity);
  }

  @Override
  public Page<Notice> search(SearchNoticeQuery query) {
    return noticeRepository.search(query).map(NoticeMapper::toDomain);
  }

  @Override
  public Optional<Notice> findById(UUID id) {
    return noticeRepository.findById(id)
        .map(NoticeMapper::toDomain);
  }

  @Override
  public void delete(UUID id) {
    noticeRepository.deleteById(id);
  }
}

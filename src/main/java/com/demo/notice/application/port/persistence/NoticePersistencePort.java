package com.demo.notice.application.port.persistence;

import com.demo.notice.application.query.SearchNoticeQuery;
import com.demo.notice.domain.model.Notice;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface NoticePersistencePort {

  Notice save(Notice notice);

  Page<Notice> search(SearchNoticeQuery query);

  Optional<Notice> findById(UUID id);

  void delete(UUID id);
}

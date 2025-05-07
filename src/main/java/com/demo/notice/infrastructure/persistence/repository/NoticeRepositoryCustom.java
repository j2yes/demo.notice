package com.demo.notice.infrastructure.persistence.repository;

import com.demo.notice.application.query.SearchNoticeQuery;
import com.demo.notice.infrastructure.persistence.entity.NoticeEntity;
import org.springframework.data.domain.Page;

public interface NoticeRepositoryCustom {

  Page<NoticeEntity> search(SearchNoticeQuery query);
}

package com.demo.notice.application.usecase;

import com.demo.notice.application.query.SearchNoticeQuery;
import com.demo.notice.domain.model.Notice;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface FindNoticeUseCase {

  Page<Notice> list(SearchNoticeQuery query);

  Notice detail(UUID id);
}

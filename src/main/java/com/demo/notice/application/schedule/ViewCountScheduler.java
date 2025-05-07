package com.demo.notice.application.schedule;

import com.demo.notice.application.port.cache.CountCachePort;
import com.demo.notice.application.port.persistence.NoticePersistencePort;
import static com.demo.notice.application.service.NoticeService.NOTICE_VIEW_COUNT_CACHE_PREFIX;
import com.demo.notice.domain.model.Notice;
import java.util.Iterator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ViewCountScheduler {

  private final NoticePersistencePort noticePersistencePort;
  private final CountCachePort countCachePort;

  @Scheduled(fixedDelay = 60000)
  public void syncViewCounts() {
    Iterator<String> iterator = countCachePort.getKeys(NOTICE_VIEW_COUNT_CACHE_PREFIX);

    while (iterator.hasNext()) {
      String idWithPrefix = iterator.next();
      String idStr = idWithPrefix.substring(NOTICE_VIEW_COUNT_CACHE_PREFIX.length());

      long viewCount = countCachePort.get(NOTICE_VIEW_COUNT_CACHE_PREFIX, idStr);

      UUID id = UUID.fromString(idStr);

      Notice notice = noticePersistencePort.findById(id).orElse(null);
      if (notice != null) {
        notice.resetViewCount(viewCount);
        noticePersistencePort.save(notice);
      }
    }
  }
}

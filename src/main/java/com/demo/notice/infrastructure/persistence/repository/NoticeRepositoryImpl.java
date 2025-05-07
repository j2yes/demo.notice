package com.demo.notice.infrastructure.persistence.repository;

import com.demo.notice.application.query.SearchNoticeQuery;
import com.demo.notice.infrastructure.persistence.entity.NoticeEntity;
import com.demo.notice.infrastructure.persistence.entity.QNoticeEntity;
import static com.demo.notice.infrastructure.persistence.entity.QNoticeEntity.noticeEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  public NoticeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Override
  public Page<NoticeEntity> search(SearchNoticeQuery query) {
    QNoticeEntity noticeEntity = QNoticeEntity.noticeEntity;

    BooleanBuilder whereCondition = getWhereCondition(query);

    List<NoticeEntity> entities = jpaQueryFactory
        .selectFrom(noticeEntity)
        .where(whereCondition)
        .offset(query.getPageRequest().getOffset())
        .limit(query.getPageRequest().getPageSize())
        .orderBy(noticeEntity.createdAt.desc())
        .fetch();

    long total = jpaQueryFactory
        .select(noticeEntity.count())
        .from(noticeEntity)
        .where(whereCondition)
        .fetchOne();

    return new PageImpl<>(entities, query.getPageRequest(), total);
  }

  private BooleanBuilder getWhereCondition(SearchNoticeQuery query) {
    BooleanBuilder builder = new BooleanBuilder();

    if (StringUtils.hasText(query.getTitle())) {
      builder.and(noticeEntity.title.contains(query.getTitle()));

      if (StringUtils.hasText(query.getContent())) {
        builder.and(noticeEntity.content.contains(query.getContent()));
      }
    }

    if (query.getFromCreatedDate() != null) {
      builder.and(noticeEntity.createdAt.goe(query.getFromCreatedDate().atStartOfDay()));
    }

    if (query.getToCreatedDate() != null) {
      builder.and(noticeEntity.createdAt.lt(
          query.getToCreatedDate().plusDays(1).atStartOfDay()));
    }
    return builder;
  }
}

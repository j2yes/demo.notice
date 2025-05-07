package com.demo.notice.infrastructure.persistence.repository;

import com.demo.notice.infrastructure.persistence.entity.NoticeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, UUID>,
    NoticeRepositoryCustom {

}

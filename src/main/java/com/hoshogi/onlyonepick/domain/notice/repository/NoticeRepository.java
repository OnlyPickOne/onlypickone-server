package com.hoshogi.onlyonepick.domain.notice.repository;

import com.hoshogi.onlyonepick.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
}

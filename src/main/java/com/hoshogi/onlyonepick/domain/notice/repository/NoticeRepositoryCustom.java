package com.hoshogi.onlyonepick.domain.notice.repository;

import com.hoshogi.onlyonepick.domain.notice.dto.request.SearchNoticeCondition;
import com.hoshogi.onlyonepick.domain.notice.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NoticeRepositoryCustom {

    Slice<Notice> search(SearchNoticeCondition condition, Pageable pageable);
}

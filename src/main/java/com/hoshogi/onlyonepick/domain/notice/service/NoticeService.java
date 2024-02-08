package com.hoshogi.onlyonepick.domain.notice.service;

import com.hoshogi.onlyonepick.domain.notice.dto.request.NoticeRequest;
import com.hoshogi.onlyonepick.domain.notice.dto.request.SearchNoticeCondition;
import com.hoshogi.onlyonepick.domain.notice.dto.response.NoticeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public interface NoticeService {

    void createNotice(NoticeRequest request);
    Slice<NoticeResponse> searchNotices(SearchNoticeCondition condition, Pageable pageable);
    NoticeResponse showNoticeInfo(Long noticeId);
    void updateNoticeInfo(NoticeRequest request, Long noticeId);
    void deleteNotice(Long noticeId);
}

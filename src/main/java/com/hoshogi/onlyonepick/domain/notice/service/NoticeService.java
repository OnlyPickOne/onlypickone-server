package com.hoshogi.onlyonepick.domain.notice.service;

import com.hoshogi.onlyonepick.domain.notice.dto.request.CreateNoticeRequest;
import com.hoshogi.onlyonepick.domain.notice.dto.response.NoticeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public interface NoticeService {

    void createNotice(CreateNoticeRequest request);
    Slice<NoticeResponse> searchNotices(Pageable pageable);
    NoticeResponse showNoticeInfo(Long noticeId);
    void updateNoticeInfo(CreateNoticeRequest request);
    void deleteNotice(Long noticeId);
}

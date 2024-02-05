package com.hoshogi.onlyonepick.domain.notice.service;

import com.hoshogi.onlyonepick.domain.notice.dto.request.CreateNoticeRequest;
import com.hoshogi.onlyonepick.domain.notice.dto.response.SimpleNoticeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface NoticeService {

    void createNotice(CreateNoticeRequest request);
    Page<SimpleNoticeResponse> showSimpleNoticeInfo(Pageable pageable);
    void updateNoticeInfo(CreateNoticeRequest request);
    void deleteNotice(Long noticeId);
}

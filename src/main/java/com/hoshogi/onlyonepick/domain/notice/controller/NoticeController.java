package com.hoshogi.onlyonepick.domain.notice.controller;

import com.hoshogi.onlyonepick.domain.notice.dto.request.CreateNoticeRequest;
import com.hoshogi.onlyonepick.domain.notice.dto.request.SearchNoticeCondition;
import com.hoshogi.onlyonepick.domain.notice.dto.response.NoticeResponse;
import com.hoshogi.onlyonepick.domain.notice.service.NoticeService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    @ResponseStatus(CREATED)
    public ApiResponse<?> createNotice(@RequestBody CreateNoticeRequest request) {
        noticeService.createNotice(request);
        return ApiResponse.onSuccess(CREATED);
    }

    @GetMapping
    public ApiResponse<Slice<NoticeResponse>> searchNotices(SearchNoticeCondition condition, Pageable pageable) {
        return ApiResponse.onSuccess(OK, noticeService.searchNotices(condition, pageable));
    }

    @GetMapping("/{notice-id}")
    public ApiResponse<NoticeResponse> showNoticeInfo(@PathVariable("notice-id") Long noticeId) {
        return ApiResponse.onSuccess(OK, noticeService.showNoticeInfo(noticeId));
    }


}

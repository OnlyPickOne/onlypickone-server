package com.hoshogi.onlyonepick.domain.notice.service;

import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.domain.notice.dto.request.NoticeRequest;
import com.hoshogi.onlyonepick.domain.notice.dto.request.SearchNoticeCondition;
import com.hoshogi.onlyonepick.domain.notice.dto.response.NoticeResponse;
import com.hoshogi.onlyonepick.domain.notice.entity.Notice;
import com.hoshogi.onlyonepick.domain.notice.repository.NoticeRepository;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.error.exception.ForbiddenException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hoshogi.onlyonepick.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final MemberService memberService;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public void createNotice(NoticeRequest request) {
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
        if (member.isNotAdmin()) {
            throw new ForbiddenException(FORBIDDEN_USER);
        }
        noticeRepository.save(request.toEntity(member));
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<NoticeResponse> searchNotices(SearchNoticeCondition condition, Pageable pageable) {
        return noticeRepository.search(condition, pageable).map(notice ->
                NoticeResponse.of(notice, false));
    }

    @Override
    @Transactional
    public NoticeResponse showNoticeInfo(Long noticeId) {
        Notice notice = findById(noticeId);
        notice.increaseViewCount();
        return NoticeResponse.of(notice, true);
    }

    @Override
    @Transactional
    public void updateNoticeInfo(NoticeRequest request, Long noticeId) {
        if (memberService.findById(SecurityUtil.getCurrentMemberId()).isNotAdmin()) {
            throw new ForbiddenException(UNAUTHORIZED_ACCESS);
        }
        Notice notice = findById(noticeId);
        notice.changeTitle(request.getTitle());
        notice.changeContent(request.getContent());
    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId) {
        if (memberService.findById(SecurityUtil.getCurrentMemberId()).isNotAdmin()) {
            throw new ForbiddenException(UNAUTHORIZED_ACCESS);
        }
        noticeRepository.delete(findById(noticeId));
    }

    private Notice findById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BadRequestException(NOTICE_NOT_FOUND));
    }
}

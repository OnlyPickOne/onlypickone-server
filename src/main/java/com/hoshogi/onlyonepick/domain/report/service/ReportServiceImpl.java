package com.hoshogi.onlyonepick.domain.report.service;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.game.service.GameService;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.domain.report.entity.Report;
import com.hoshogi.onlyonepick.domain.report.repository.ReportRepository;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MemberService memberService;
    private final GameService gameService;
    private final ReportRepository reportRepository;

    @Override
    @Transactional
    public void reportGame(Long gameId) {
        Game game = gameService.findById(gameId);
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());

        if (reportRepository.existsByMemberAndGame(member, game)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_REPORT);
        }

        game.increaseReportCount();
        reportRepository.save(Report.create(member, game));
    }
}

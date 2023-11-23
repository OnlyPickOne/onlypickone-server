package com.hoshogi.onlyonepick.domain.report.service;

import com.hoshogi.onlyonepick.domain.game.service.GameService;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.domain.report.dto.request.ReportGameRequest;
import com.hoshogi.onlyonepick.domain.report.entity.Report;
import com.hoshogi.onlyonepick.domain.report.repository.ReportRepository;
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
    public void reportGame(ReportGameRequest request) {
        reportRepository.save(Report.create(
                memberService.findById(SecurityUtil.getCurrentMemberId()),
                gameService.findById(request.getGameId()))
        );
    }
}

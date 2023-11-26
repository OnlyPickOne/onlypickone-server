package com.hoshogi.onlyonepick.domain.report.repository;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Boolean existsByMemberAndGame(Member member, Game game);
}

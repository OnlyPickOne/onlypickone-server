package com.hoshogi.onlyonepick.domain.report.entity;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.global.common.entity.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE report SET is_deleted = true WHERE report_id = ?")
@Where(clause = "is_deleted = false")
public class Report extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Builder
    public Report(Long id, Boolean isDeleted, Member member, Game game) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.member = member;
        this.game = game;
    }

    public static Report create(Member member, Game game) {
        return Report.builder()
                .isDeleted(false)
                .member(member)
                .game(game)
                .build();
    }
}

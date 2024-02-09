package com.hoshogi.onlyonepick.domain.game.entity;

import com.hoshogi.onlyonepick.domain.item.entity.Item;
import com.hoshogi.onlyonepick.domain.like.entity.Like;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.report.entity.Report;
import com.hoshogi.onlyonepick.global.common.entity.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE game SET is_deleted = true WHERE game_id = ?")
@Where(clause = "is_deleted = false and report_count < 10")
public class Game extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(name = "play_count", columnDefinition = "bigint not null default 0")
    private Long playCount;

    @Column(name = "like_count", columnDefinition = "bigint not null default 0")
    private Long likeCount;

    @Column(name = "item_count", columnDefinition = "bigint not null default 0")
    private Long itemCount;

    @Column(name = "report_count", columnDefinition = "bigint not null default 0")
    private Long reportCount;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1) not null default 0")
    private Boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Game(Long id, String title, String description, Long playCount, Long likeCount, Long itemCount,
                Long reportCount, Boolean isDeleted, Member member) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.playCount = playCount;
        this.likeCount = likeCount;
        this.itemCount = itemCount;
        this.reportCount = reportCount;
        this.isDeleted = isDeleted;
        this.member = member;
    }

    public static Game create(String title, String description, Member member) {
        return Game.builder()
                .title(title)
                .description(description)
                .playCount(0L)
                .likeCount(0L)
                .itemCount(0L)
                .reportCount(0L)
                .isDeleted(false)
                .member(member)
                .build();
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
        itemCount += items.size();
    }

    public void increasePlayCount() {
        playCount++;
    }

    public void increaseLikeCount() {
        likeCount++;
    }

    public void increaseReportCount() {
        reportCount++;
    }

    public void decreaseLikeCount() {
        likeCount--;
    }
}

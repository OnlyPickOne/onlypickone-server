package com.hoshogi.onlyonepick.domain.game.entity;

import com.hoshogi.onlyonepick.domain.item.entity.Item;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
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
@SQLDelete(sql = "UPDATE game SET deleted = true WHERE game_id = ?")
@Where(clause = "deleted = false and report_count < 10")
public class Game extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(nullable = false, length = 300)
    private String description;

    @Column(name = "view_count", columnDefinition = "bigint not null default 0")
    private Long viewCount;

    @Column(name = "item_count", columnDefinition = "bigint not null default 0")
    private Long itemCount;

    @Column(name = "report_count", columnDefinition = "bigint not null default 0")
    private Long reportCount;

    @Column(columnDefinition = "tinyint(1) not null default 0")
    private Boolean deleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @Builder
    public Game(Long gameId, String title, String description, Long viewCount, Long itemCount, Long reportCount,
                Boolean deleted, Member member) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.itemCount = itemCount;
        this.reportCount = reportCount;
        this.deleted = deleted;
        this.member = member;
    }

    public static Game create(String title, String description, Member member) {
        return Game.builder()
                .title(title)
                .description(description)
                .viewCount(0L)
                .itemCount(0L)
                .reportCount(0L)
                .deleted(false)
                .member(member)
                .build();
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
        itemCount = Long.valueOf(this.items.size());
    }
}

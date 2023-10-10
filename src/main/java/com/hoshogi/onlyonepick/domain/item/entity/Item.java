package com.hoshogi.onlyonepick.domain.item.entity;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.global.common.entity.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE item SET deleted = true WHERE item_id = ?")
@Where(clause = "deleted = false")
public class Item extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(nullable = false, length = 2083)
    private String imageUrl;

    @Column(nullable = false, length = 50)
    private String caption;

    @Column(name = "win_count", columnDefinition = "bigint not null default 0")
    private Long winCount;

    @Column(columnDefinition = "tinyint(1) not null default 0")
    private Boolean deleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Builder
    public Item(Long itemId, String imageUrl, String caption, Long winCount, Boolean deleted, Game game) {
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.winCount = winCount;
        this.deleted = deleted;
        this.game = game;
    }

    public static Item create(String imageUrl, String caption, Game game) {
        return Item.builder()
                .imageUrl(imageUrl)
                .caption(caption)
                .winCount(0L)
                .deleted(false)
                .game(game)
                .build();
    }
}

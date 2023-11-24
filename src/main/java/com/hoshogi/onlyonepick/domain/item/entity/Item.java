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
@SQLDelete(sql = "UPDATE item SET is_deleted = true WHERE item_id = ?")
@Where(clause = "is_deleted = false")
public class Item extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, length = 2083)
    private String imageUrl;

    @Column(nullable = false, length = 50)
    private String caption;

    @Column(name = "win_count", columnDefinition = "bigint not null default 0")
    private Long winCount;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1) not null default 0")
    private Boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Builder
    public Item(Long id, String imageUrl, String caption, Long winCount, Boolean isDeleted, Game game) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.winCount = winCount;
        this.isDeleted = isDeleted;
        this.game = game;
    }

    public static Item create(String imageUrl, String caption, Game game) {
        return Item.builder()
                .imageUrl(imageUrl)
                .caption(caption)
                .winCount(0L)
                .isDeleted(false)
                .game(game)
                .build();
    }

    public void increaseWinCount() {
        winCount++;
    }

    public Double getStats() {
        return 100.0 * winCount / this.getGame().getPlayCount();
    }
}

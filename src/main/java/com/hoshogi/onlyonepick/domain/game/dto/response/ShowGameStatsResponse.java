package com.hoshogi.onlyonepick.domain.game.dto.response;

import com.hoshogi.onlyonepick.domain.item.entity.Item;
import lombok.Getter;

@Getter
public class ShowGameStatsResponse {

    private Long itemId;
    private String imageUrl;
    private String caption;
    private Long winCount;
    private Double stats;

    public ShowGameStatsResponse(Item item) {
        this.itemId = item.getId();
        this.imageUrl = item.getImageUrl();
        this.caption = item.getCaption();
        this.winCount = item.getWinCount();
        this.stats = item.getStats();
    }
}

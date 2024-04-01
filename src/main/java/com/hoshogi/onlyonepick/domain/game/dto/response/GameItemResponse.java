package com.hoshogi.onlyonepick.domain.game.dto.response;

import com.hoshogi.onlyonepick.domain.item.entity.Item;
import lombok.Getter;

@Getter
public class GameItemResponse {

    private Long itemId;
    private String imageUrl;
    private String caption;

    public GameItemResponse(Item item) {
        this.itemId = item.getId();
        this.imageUrl = item.getImageUrl();
        this.caption = item.getCaption();
    }
}

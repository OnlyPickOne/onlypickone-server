package com.hoshogi.onlyonepick.domain.game.dto.response;

import com.hoshogi.onlyonepick.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ShowGameItemResponse {

    private Long itemId;
    private String imageUrl;
    private String caption;

    public ShowGameItemResponse(Item item) {
        this.itemId = item.getItemId();
        this.imageUrl = item.getImageUrl();
        this.caption = item.getCaption();
    }
}

package com.hoshogi.onlyonepick.domain.game.dto.response;

import com.hoshogi.onlyonepick.domain.item.entity.Item;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GameStatsResponse {

    private Long playCount;
    private List<ItemInfo> items;

    public GameStatsResponse(Long playCount, List<Item> items) {
        this.playCount = playCount;
        this.items = mappingItemInfo(items);
    }

    @Getter
    private static class ItemInfo {
        private Long itemId;
        private String imageUrl;
        private String caption;
        private Long winCount;
        private Double stats;

        public ItemInfo(Item item) {
            this.itemId = item.getId();
            this.imageUrl = item.getImageUrl();
            this.caption = item.getCaption();
            this.winCount = item.getWinCount();
            this.stats = item.getStats();
        }
    }

    private List<ItemInfo> mappingItemInfo(List<Item> items) {
        return items.stream()
                .map(ItemInfo::new)
                .collect(Collectors.toList());
    }
}

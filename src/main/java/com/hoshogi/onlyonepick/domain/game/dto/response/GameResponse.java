package com.hoshogi.onlyonepick.domain.game.dto.response;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import lombok.Getter;

import java.util.List;

@Getter
public class GameResponse {

    private Long gameId;
    private String title;
    private String description;
    private Long viewCount;
    private Long playCount;
    private Long itemCount;
    private Long reportCount;
    private List<String> imageUrls;

    public GameResponse(Game game, List<String> imageUrls) {
        this.gameId = game.getGameId();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.viewCount = game.getViewCount();
        this.playCount = game.getPlayCount();
        this.itemCount = game.getItemCount();
        this.reportCount = game.getReportCount();
        this.imageUrls = imageUrls;
    }
}

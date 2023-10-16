package com.hoshogi.onlyonepick.domain.game.dto.response;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GameResponse {

    private Long gameId;
    private String title;
    private String description;
    private Long playCount;
    private Long likeCount;
    private Long itemCount;
    private Long reportCount;
    private LocalDateTime createdAt;
    private List<String> imageUrls;

    public GameResponse(Game game, List<String> imageUrls) {
        this.gameId = game.getGameId();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.playCount = game.getPlayCount();
        this.likeCount = game.getLikeCount();
        this.itemCount = game.getItemCount();
        this.reportCount = game.getReportCount();
        this.createdAt = game.getCreatedAt();
        this.imageUrls = imageUrls;
    }
}

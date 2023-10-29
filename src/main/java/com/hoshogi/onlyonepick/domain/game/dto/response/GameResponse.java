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
    private Boolean isLiked;
    private Boolean isCreated;
    private LocalDateTime createdAt;
    private List<String> imageUrls;

    public GameResponse(Game game, Boolean isLiked, Boolean isCreated, List<String> imageUrls) {
        this.gameId = game.getGameId();
        this.title = game.getTitle();
        this.description = game.getDescription();
        this.playCount = game.getPlayCount();
        this.likeCount = game.getLikeCount();
        this.itemCount = game.getItemCount();
        this.reportCount = game.getReportCount();
        this.isLiked = isLiked;
        this.isCreated = isCreated;
        this.createdAt = game.getCreatedAt();
        this.imageUrls = imageUrls;
    }
}

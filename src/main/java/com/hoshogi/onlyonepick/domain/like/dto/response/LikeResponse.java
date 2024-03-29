package com.hoshogi.onlyonepick.domain.like.dto.response;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponse {

    private Long gameId;
    private Long likeCount;

    public static LikeResponse of(Game game) {
        return new LikeResponse(game.getId(), game.getLikeCount());
    }
}

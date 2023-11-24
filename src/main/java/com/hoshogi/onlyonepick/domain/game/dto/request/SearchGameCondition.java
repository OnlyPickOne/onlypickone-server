package com.hoshogi.onlyonepick.domain.game.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SearchGameCondition {

    private Long gameId;
    private Long memberId;
    private Long playCount;
    private Long likeCount;
    private LocalDateTime createdAt;
    private String query;

}

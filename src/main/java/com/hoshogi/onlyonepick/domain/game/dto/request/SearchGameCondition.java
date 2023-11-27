package com.hoshogi.onlyonepick.domain.game.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SearchGameCondition {

    private Long gameId;
    private Long memberId;
    private Long playCount;
    private Long likeCount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;
    private String query;
}

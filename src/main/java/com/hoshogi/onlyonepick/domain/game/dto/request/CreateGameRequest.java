package com.hoshogi.onlyonepick.domain.game.dto.request;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateGameRequest {

    private String title;
    private String description;

    public Game toEntity(Member member) {
        return Game.create(title, description, member);
    }
}

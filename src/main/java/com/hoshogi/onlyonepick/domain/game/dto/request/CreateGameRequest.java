package com.hoshogi.onlyonepick.domain.game.dto.request;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateGameRequest {

    private String title;
    private String description;
    private List<MultipartFile> multipartFiles;

    public Game toEntity(Member member) {
        return Game.create(title, description, member);
    }
}

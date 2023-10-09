package com.hoshogi.onlyonepick.domain.game.service;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GameService {

    void createGame(CreateGameRequest request, List<MultipartFile> multipartFiles);
}

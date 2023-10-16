package com.hoshogi.onlyonepick.domain.game.controller;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import com.hoshogi.onlyonepick.domain.game.dto.response.GameResponse;
import com.hoshogi.onlyonepick.domain.game.service.GameService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(CREATED)
    ApiResponse<?> createGame(@ModelAttribute CreateGameRequest request) {
        gameService.createGame(request);
        return ApiResponse.onSuccess(CREATED);
    }

    @GetMapping
    ApiResponse<Page<GameResponse>> showGames(Pageable pageable) {
        return ApiResponse.onSuccess(OK, gameService.showGames(pageable));
    }
}

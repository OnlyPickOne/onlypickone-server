package com.hoshogi.onlyonepick.domain.game.controller;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import com.hoshogi.onlyonepick.domain.game.dto.request.SearchGameCondition;
import com.hoshogi.onlyonepick.domain.game.dto.request.ShowGameStatsRequest;
import com.hoshogi.onlyonepick.domain.game.dto.response.GameResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.ShowGameItemResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.ShowGameStatsResponse;
import com.hoshogi.onlyonepick.domain.game.service.GameService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping()
    ApiResponse<Slice<GameResponse>> searchGames(SearchGameCondition condition, Pageable pageable) {
        return ApiResponse.onSuccess(OK, gameService.searchGames(condition, pageable));
    }

    @DeleteMapping("/{game-id}")
    ApiResponse<?> deleteGame(@PathVariable("game-id") Long gameId) {
        gameService.deleteGame(gameId);
        return ApiResponse.onSuccess(OK);
    }

    @PostMapping({"/{game-id}/items"})
    ApiResponse<List<ShowGameStatsResponse>> showGameStats(@PathVariable("game-id") Long gameId,
                                                           @RequestBody ShowGameStatsRequest request) {
        return ApiResponse.onSuccess(OK, gameService.showGameStats(request, gameId));
    }

    @GetMapping("/{game-id}/items")
    ApiResponse<List<ShowGameItemResponse>> showGameItems(@PathVariable("game-id") Long gameId,
                                                          @RequestParam Long count) {
        return ApiResponse.onSuccess(OK, gameService.showGameItems(gameId, count));
    }
}

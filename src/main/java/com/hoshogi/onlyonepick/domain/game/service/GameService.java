package com.hoshogi.onlyonepick.domain.game.service;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import com.hoshogi.onlyonepick.domain.game.dto.request.ShowGameStatsRequest;
import com.hoshogi.onlyonepick.domain.game.dto.response.GameResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.ShowGameItemResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.ShowGameStatsResponse;
import com.hoshogi.onlyonepick.domain.game.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameService {

    void createGame(CreateGameRequest request);
    Page<GameResponse> showGames(Pageable pageable);
    void deleteGame(Long gameId);
    List<ShowGameStatsResponse> showGameStats(ShowGameStatsRequest request, Long gameId);
    List<ShowGameItemResponse> showGameItems(Long gameId, Long count);
    Game findById(Long gameId);
}

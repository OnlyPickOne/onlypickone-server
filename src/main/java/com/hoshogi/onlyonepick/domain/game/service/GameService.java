package com.hoshogi.onlyonepick.domain.game.service;

import com.hoshogi.onlyonepick.domain.game.dto.request.CreateGameRequest;
import com.hoshogi.onlyonepick.domain.game.dto.request.SearchGameCondition;
import com.hoshogi.onlyonepick.domain.game.dto.request.ShowGameStatsRequest;
import com.hoshogi.onlyonepick.domain.game.dto.response.GameResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.GameItemResponse;
import com.hoshogi.onlyonepick.domain.game.dto.response.GameStatsResponse;
import com.hoshogi.onlyonepick.domain.game.entity.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface GameService {

    void createGame(CreateGameRequest request);
    Slice<GameResponse> searchGames(SearchGameCondition condition, Pageable pageable);
    GameResponse showGameInfo(Long gameId);
    void deleteGame(Long gameId);
    GameStatsResponse showGameStats(ShowGameStatsRequest request, Long gameId);
    List<GameItemResponse> showGameItems(Long gameId, int count);
    Game findById(Long gameId);
}

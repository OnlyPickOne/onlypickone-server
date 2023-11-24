package com.hoshogi.onlyonepick.domain.game.repository;

import com.hoshogi.onlyonepick.domain.game.dto.request.SearchGameCondition;
import com.hoshogi.onlyonepick.domain.game.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameRepositoryCustom {

    Page<Game> search(SearchGameCondition condition, Pageable pageable);
}

package com.hoshogi.onlyonepick.domain.game.repository;

import com.hoshogi.onlyonepick.domain.game.dto.request.SearchGameCondition;
import com.hoshogi.onlyonepick.domain.game.entity.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GameRepositoryCustom {

    Slice<Game> search(SearchGameCondition condition, Pageable pageable);
}

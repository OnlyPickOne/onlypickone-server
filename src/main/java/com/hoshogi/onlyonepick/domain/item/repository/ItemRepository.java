package com.hoshogi.onlyonepick.domain.item.repository;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByGameOrderByWinCountDesc(Game game);
    @Query(value = "SELECT * FROM item WHERE game_id = :gameId ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Item> findRandomByGame(@Param("gameId") Long gameId, @Param("limit") Long limit);
}

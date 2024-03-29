package com.hoshogi.onlyonepick.domain.like.repository;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.like.entity.Like;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "select like " +
            "from Like like " +
            "where like.member = :member and like.game = :game " +
            "order by like.createdAt desc " +
            "limit 1")
    Optional<Like> findByMemberAndGame(@Param("member") Member member, Game game);
}

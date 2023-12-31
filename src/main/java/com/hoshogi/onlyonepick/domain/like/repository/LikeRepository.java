package com.hoshogi.onlyonepick.domain.like.repository;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.like.entity.Like;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Boolean existsByMemberAndGame(Member member, Game game);
    Optional<Like> findByMemberAndGame(Member member, Game game);
}

package com.hoshogi.onlyonepick.domain.like.service;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.game.service.GameService;
import com.hoshogi.onlyonepick.domain.like.dto.response.LikeResponse;
import com.hoshogi.onlyonepick.domain.like.entity.Like;
import com.hoshogi.onlyonepick.domain.like.repository.LikeRepository;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hoshogi.onlyonepick.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final MemberService memberService;
    private final GameService gameService;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public LikeResponse likeGame(Long gameId) {
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
        Game game = gameService.findById(gameId);
        likeRepository.findByMemberAndGame(member, game).ifPresentOrElse(
                like -> releaseLikeIsDeleted(like),
                () -> likeRepository.save(Like.create(member, game))
        );
        game.increaseLikeCount();
        return LikeResponse.of(game);
    }

    @Override
    @Transactional
    public LikeResponse deleteLike(Long gameId) {
        Game game = gameService.findById(gameId);
        Like like = likeRepository.findByMemberAndGame(memberService.findById(SecurityUtil.getCurrentMemberId()), game)
                .orElseThrow(() -> new BadRequestException(LIKE_NOT_FOUND));
        game.decreaseLikeCount();
        likeRepository.delete(like);
        return LikeResponse.of(game);
    }

    private void releaseLikeIsDeleted(Like like) {
        if (like.isNotDeleted()) {
            throw new BadRequestException(DUPLICATE_LIKE);
        }
        like.releaseIsDeleted();
    }
}

package com.hoshogi.onlyonepick.domain.like.service;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.game.service.GameService;
import com.hoshogi.onlyonepick.domain.like.entity.Like;
import com.hoshogi.onlyonepick.domain.like.repository.LikeRepository;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final MemberService memberService;
    private final GameService gameService;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void likeGame(Long gameId) {
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
        Game game = gameService.findById(gameId);
        if (likeRepository.existsByMemberAndGame(member, game)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_LIKE);
        }
        game.increaseLikeCount();
        likeRepository.save(Like.create(member, game));
    }

    @Override
    @Transactional
    public void deleteLike(Long gameId) {
        Game game = gameService.findById(gameId);
        Like like = likeRepository.findByMemberAndGame(memberService.findById(SecurityUtil.getCurrentMemberId()), game)
                .orElseThrow(() -> new BadRequestException(ErrorCode.LIKE_NOT_FOUND));
        game.decreaseLikeCount();
        likeRepository.delete(like);
    }
}

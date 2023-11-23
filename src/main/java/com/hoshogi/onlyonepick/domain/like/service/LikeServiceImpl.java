package com.hoshogi.onlyonepick.domain.like.service;

import com.hoshogi.onlyonepick.domain.game.entity.Game;
import com.hoshogi.onlyonepick.domain.game.service.GameService;
import com.hoshogi.onlyonepick.domain.like.dto.request.CreateLikeRequest;
import com.hoshogi.onlyonepick.domain.like.entity.Like;
import com.hoshogi.onlyonepick.domain.like.repository.LikeRepository;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.error.exception.ForbiddenException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private MemberService memberService;
    private GameService gameService;
    private LikeRepository likeRepository;

    @Override
    @Transactional
    public void likeGame(CreateLikeRequest request) {
        Member member = memberService.findById(SecurityUtil.getCurrentMemberId());
        Game game = gameService.findById(request.getGameId());
        if (likeRepository.existsByMemberAndGame(member, game)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_LIKE);
        }
        game.increaseLikeCount();
        likeRepository.save(Like.create(member, game));
    }

    @Override
    @Transactional
    public void cancelLike(Long likeId) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.LIKE_NOT_FOUND));
        if (like.getMember().getId() != SecurityUtil.getCurrentMemberId()) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER);
        }
        like.getGame().decreaseLikeCount();
        likeRepository.delete(like);
    }
}

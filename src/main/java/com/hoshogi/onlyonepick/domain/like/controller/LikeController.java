package com.hoshogi.onlyonepick.domain.like.controller;

import com.hoshogi.onlyonepick.domain.like.dto.response.LikeResponse;
import com.hoshogi.onlyonepick.domain.like.service.LikeService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games/{game-id}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ApiResponse<LikeResponse> createLike(@PathVariable("game-id") Long gameId) {
        return ApiResponse.onSuccess(OK, likeService.likeGame(gameId));
    }

    @DeleteMapping
    public ApiResponse<LikeResponse> deleteLike(@PathVariable("game-id") Long gameId) {
        return ApiResponse.onSuccess(OK, likeService.deleteLike(gameId));
    }
}

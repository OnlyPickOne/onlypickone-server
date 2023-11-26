package com.hoshogi.onlyonepick.domain.like.controller;

import com.hoshogi.onlyonepick.domain.like.dto.request.CreateLikeRequest;
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
    public ApiResponse<?> createLike(@PathVariable("game-id") Long gameId) {
        likeService.likeGame(gameId);
        return ApiResponse.onSuccess(OK);
    }

    @DeleteMapping
    public ApiResponse<?> deleteLike(@PathVariable("game-id") Long gameId) {
        likeService.deleteLike(gameId);
        return ApiResponse.onSuccess(OK);
    }
}

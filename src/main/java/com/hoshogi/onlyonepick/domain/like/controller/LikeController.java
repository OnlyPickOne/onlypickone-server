package com.hoshogi.onlyonepick.domain.like.controller;

import com.hoshogi.onlyonepick.domain.like.dto.request.CreateLikeRequest;
import com.hoshogi.onlyonepick.domain.like.service.LikeService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ApiResponse<?> createLike(CreateLikeRequest request) {
        likeService.likeGame(request);
        return ApiResponse.onSuccess(OK);
    }

    @DeleteMapping("/{like-id}")
    public ApiResponse<?> deleteLike(@PathVariable("like-id") Long likeId) {
        likeService.cancelLike(likeId);
        return ApiResponse.onSuccess(OK);
    }
}

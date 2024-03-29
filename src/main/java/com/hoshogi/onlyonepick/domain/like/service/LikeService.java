package com.hoshogi.onlyonepick.domain.like.service;

import com.hoshogi.onlyonepick.domain.like.dto.response.LikeResponse;

public interface LikeService {

    LikeResponse likeGame(Long gameId);
    LikeResponse deleteLike(Long gameId);
}

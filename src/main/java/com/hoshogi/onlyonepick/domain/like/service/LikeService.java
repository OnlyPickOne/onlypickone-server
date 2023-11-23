package com.hoshogi.onlyonepick.domain.like.service;

import com.hoshogi.onlyonepick.domain.like.dto.request.CreateLikeRequest;

public interface LikeService {

    void likeGame(CreateLikeRequest request);
    void cancelLike(Long likeId);
}

package com.hoshogi.onlyonepick.domain.like.service;

public interface LikeService {

    void likeGame(Long gameId);
    void deleteLike(Long gameId);
}

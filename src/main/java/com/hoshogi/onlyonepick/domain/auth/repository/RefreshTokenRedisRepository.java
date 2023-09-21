package com.hoshogi.onlyonepick.domain.auth.repository;

import com.hoshogi.onlyonepick.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}

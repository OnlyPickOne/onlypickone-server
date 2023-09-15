package com.hoshogi.onlyonepick.domain.version.respository;

import com.hoshogi.onlyonepick.domain.version.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VersionRepository extends JpaRepository<Version, Long> {

     Optional<Version> findTopByOrderByCreatedAtDesc();
}

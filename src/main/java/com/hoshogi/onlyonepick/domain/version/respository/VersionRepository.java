package com.hoshogi.onlyonepick.domain.version.respository;

import com.hoshogi.onlyonepick.domain.version.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {

     Optional<Version> findTopByOrderByCreatedAtDesc();
}

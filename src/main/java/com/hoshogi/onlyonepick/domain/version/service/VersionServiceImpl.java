package com.hoshogi.onlyonepick.domain.version.service;

import com.hoshogi.onlyonepick.domain.version.dto.request.VersionRequest;
import com.hoshogi.onlyonepick.domain.version.dto.response.VersionResponse;
import com.hoshogi.onlyonepick.domain.version.entity.Version;
import com.hoshogi.onlyonepick.domain.version.respository.VersionRepository;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {

    private final VersionRepository versionRepository;

    @Override
    public VersionResponse findVersion() {
        Version version = versionRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new BadRequestException(ErrorCode.VERSION_NOT_FOUND));
        return VersionResponse.from(version);
    }

    @Override
    public void saveVersion(VersionRequest request) {
        versionRepository.save(request.toEntity());
    }
}

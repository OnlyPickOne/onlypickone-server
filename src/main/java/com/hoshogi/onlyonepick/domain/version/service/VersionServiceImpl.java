package com.hoshogi.onlyonepick.domain.version.service;

import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.domain.version.dto.request.VersionRequest;
import com.hoshogi.onlyonepick.domain.version.dto.response.VersionResponse;
import com.hoshogi.onlyonepick.domain.version.entity.Version;
import com.hoshogi.onlyonepick.domain.version.respository.VersionRepository;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.error.exception.ForbiddenException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {

    private final VersionRepository versionRepository;
    private final MemberService memberService;

    @Override
    @Transactional(readOnly = true)
    public VersionResponse findVersion() {
        Version version = versionRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new BadRequestException(ErrorCode.VERSION_NOT_FOUND));
        return VersionResponse.of(version);
    }

    @Override
    @Transactional
    public void saveVersion(VersionRequest request) {
        if (!memberService.findById(SecurityUtil.getCurrentMemberId()).isAdmin()) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER);
        }
        versionRepository.save(request.toEntity());
    }
}

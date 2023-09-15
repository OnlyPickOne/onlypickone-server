package com.hoshogi.onlyonepick.domain.version.service;

import com.hoshogi.onlyonepick.domain.version.dto.request.VersionRequest;
import com.hoshogi.onlyonepick.domain.version.dto.response.VersionResponse;

public interface VersionService {

    void saveVersion(VersionRequest request);
    VersionResponse findVersion();
}

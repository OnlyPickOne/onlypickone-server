package com.hoshogi.onlyonepick.domain.version.dto.response;

import com.hoshogi.onlyonepick.domain.version.entity.Version;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VersionResponse {

    private Long versionId;
    private String minimum;
    private String latest;

    @Builder
    public VersionResponse(Long versionId, String minimum, String latest) {
        this.versionId = versionId;
        this.minimum = minimum;
        this.latest = latest;
    }

    public static VersionResponse from(Version version) {
        return VersionResponse.builder()
                .versionId(version.getVersionId())
                .minimum(version.getMinimum())
                .latest(version.getLatest())
                .build();
    }
}

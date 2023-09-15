package com.hoshogi.onlyonepick.domain.version.dto.request;

import com.hoshogi.onlyonepick.domain.version.entity.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VersionRequest {

    private String minimum;
    private String latest;

    public Version toEntity() {
        return Version.builder()
                .minimum(minimum)
                .latest(latest)
                .build();
    }
}

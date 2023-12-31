package com.hoshogi.onlyonepick.domain.version.entity;

import com.hoshogi.onlyonepick.global.common.entity.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Version extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String minimum;

    @Column(length = 20, nullable = false)
    private String latest;

    @Builder
    public Version(Long id, String minimum, String latest) {
        this.id = id;
        this.minimum = minimum;
        this.latest = latest;
    }
}

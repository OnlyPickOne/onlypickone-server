package com.hoshogi.onlyonepick.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequest {

    private String accessToken;
    private String refreshToken;
}

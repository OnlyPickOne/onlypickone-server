package com.hoshogi.onlyonepick.domain.auth.service;

import com.hoshogi.onlyonepick.domain.auth.dto.request.AuthRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.request.TokenRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.response.TokenResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    void signUp(AuthRequest request);
    TokenResponse login(AuthRequest request);
    TokenResponse reissueToken(TokenRequest request);
}

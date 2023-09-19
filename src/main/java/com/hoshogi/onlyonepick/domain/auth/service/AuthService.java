package com.hoshogi.onlyonepick.domain.auth.service;

import com.hoshogi.onlyonepick.domain.auth.dto.request.LoginRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.request.SignUpRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.request.TokenRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.response.TokenResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    void signUp(SignUpRequest request);
    TokenResponse login(LoginRequest request);
    TokenResponse reissueToken(TokenRequest request);
}

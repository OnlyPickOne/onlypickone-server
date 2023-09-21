package com.hoshogi.onlyonepick.domain.auth.controller;

import com.hoshogi.onlyonepick.domain.auth.dto.request.AuthRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.request.TokenRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.response.TokenResponse;
import com.hoshogi.onlyonepick.domain.auth.service.AuthService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(CREATED)
    ApiResponse<?> signUp(@RequestBody AuthRequest request) {
        authService.signUp(request);
        return ApiResponse.onSuccess(CREATED);
    }

    @PostMapping("/login")
    ApiResponse<TokenResponse> login(@RequestBody AuthRequest request) {
        return ApiResponse.onSuccess(OK, authService.login(request));
    }

    @PostMapping("/reissue")
    ApiResponse<TokenResponse> reissue(@RequestBody TokenRequest request) {
        return ApiResponse.onSuccess(OK, authService.reissueToken(request));
    }
}

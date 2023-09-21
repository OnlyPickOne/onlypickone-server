package com.hoshogi.onlyonepick.domain.auth.service;

import com.hoshogi.onlyonepick.domain.auth.dto.request.AuthRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.request.TokenRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.response.TokenResponse;
import com.hoshogi.onlyonepick.domain.auth.entity.RefreshToken;
import com.hoshogi.onlyonepick.domain.auth.repository.RefreshTokenRedisRepository;
import com.hoshogi.onlyonepick.domain.member.repository.MemberRepository;
import com.hoshogi.onlyonepick.domain.member.service.VerifyMemberService;
import com.hoshogi.onlyonepick.global.config.jwt.TokenProvider;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final VerifyMemberService verifyMemberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final Long TIME_TO_LIVE = 60 * 60 * 24 * 14L;

    @Override
    @Transactional
    public void signUp(AuthRequest request) {
        verifyMemberService.verifyMemberIsDuplicated(request.getEmail());
        memberRepository.save(request.toEntity(passwordEncoder));
    }

    @Override
    @Transactional
    public TokenResponse login(AuthRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenResponse response = tokenProvider.generateToken(authentication, request.getEmail());
        RefreshToken refreshToken = RefreshToken.builder()
                .token(response.getRefreshToken())
                .memberId(authentication.getName())
                .ttl(TIME_TO_LIVE)
                .build();
        refreshTokenRedisRepository.save(refreshToken);
        return response;
    }

    @Override
    public TokenResponse reissueToken(TokenRequest request) {
        return null;
    }
}

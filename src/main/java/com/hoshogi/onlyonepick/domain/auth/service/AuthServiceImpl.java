package com.hoshogi.onlyonepick.domain.auth.service;

import com.hoshogi.onlyonepick.domain.auth.dto.request.LoginRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.request.SignUpRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.request.TokenRequest;
import com.hoshogi.onlyonepick.domain.auth.dto.response.TokenResponse;
import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.repository.MemberRepository;
import com.hoshogi.onlyonepick.domain.member.service.VerifyMemberService;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final VerifyMemberService verifyMemberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequest request) {
        verifyMemberService.verifyMemberIsDuplicated(request.getEmail());
        memberRepository.save(request.toEntity(passwordEncoder));
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public TokenResponse reissueToken(TokenRequest request) {
        return null;
    }
}

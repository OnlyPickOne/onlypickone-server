package com.hoshogi.onlyonepick.domain.member.service;

import com.hoshogi.onlyonepick.domain.member.repository.MemberRepository;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyMemberServiceImpl implements VerifyMemberService {

    private final MemberRepository memberRepository;

    @Override
    public void verifyMemberIsDuplicated(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_MEMBER);
        }
    }
}

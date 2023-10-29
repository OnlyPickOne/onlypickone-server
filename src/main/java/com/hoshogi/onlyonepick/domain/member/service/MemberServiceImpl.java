package com.hoshogi.onlyonepick.domain.member.service;

import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.member.repository.MemberRepository;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.error.exception.ForbiddenException;
import com.hoshogi.onlyonepick.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public void deleteMember(Long memberId) {
        Member member = findById(SecurityUtil.getCurrentMemberId());
        if (!member.isAdmin() && member.getMemberId() != memberId) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_USER);
        }
        memberRepository.delete(findById(memberId));
    }

    @Override
    public void verifyMemberIsDuplicated(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_MEMBER);
        }
    }
}

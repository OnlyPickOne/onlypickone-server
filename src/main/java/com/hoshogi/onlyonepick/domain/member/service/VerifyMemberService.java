package com.hoshogi.onlyonepick.domain.member.service;

import com.hoshogi.onlyonepick.domain.member.entity.Member;

public interface VerifyMemberService {

    void verifyMemberIsDuplicated(String email);
    Member findById(Long memberId);
}

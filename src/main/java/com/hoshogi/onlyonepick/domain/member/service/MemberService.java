package com.hoshogi.onlyonepick.domain.member.service;

import com.hoshogi.onlyonepick.domain.member.entity.Member;

public interface MemberService {

    Member findById(Long memberId);
    void verifyMemberIsDuplicated(String email);
}

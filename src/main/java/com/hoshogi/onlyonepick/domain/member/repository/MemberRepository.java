package com.hoshogi.onlyonepick.domain.member.repository;

import com.hoshogi.onlyonepick.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByEmail(String email);
}

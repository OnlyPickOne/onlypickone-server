package com.hoshogi.onlyonepick.domain.member.controller;

import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping("/{member-id}")
    public ApiResponse<?> delete(@PathVariable("member-id") Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResponse.onSuccess(OK);
    }
}

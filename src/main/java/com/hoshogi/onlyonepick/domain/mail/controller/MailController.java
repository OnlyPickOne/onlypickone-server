package com.hoshogi.onlyonepick.domain.mail.controller;

import com.hoshogi.onlyonepick.domain.mail.dto.request.SendCodeRequest;
import com.hoshogi.onlyonepick.domain.mail.dto.request.VerifyCodeRequest;
import com.hoshogi.onlyonepick.domain.mail.service.MailService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mails")
public class MailController {

    private final MailService mailService;

    @PostMapping
    public ApiResponse<?> sendCode(@RequestBody SendCodeRequest request) {
        mailService.sendAuthCodeToEmail(request);
        return ApiResponse.onSuccess(OK);
    }

    @PostMapping("/verify")
    public ApiResponse<?> verifyCode(@RequestBody VerifyCodeRequest request) {
        mailService.verifyAuthCode(request);
        return ApiResponse.onSuccess(OK);
    }
}

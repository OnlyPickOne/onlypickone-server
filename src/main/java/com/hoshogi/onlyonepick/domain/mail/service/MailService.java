package com.hoshogi.onlyonepick.domain.mail.service;

import com.hoshogi.onlyonepick.domain.mail.dto.request.SendCodeRequest;
import com.hoshogi.onlyonepick.domain.mail.dto.request.VerifyCodeRequest;

public interface MailService {

    void sendAuthCodeToEmail(SendCodeRequest request);
    void verifyAuthCode(VerifyCodeRequest request);
}

package com.hoshogi.onlyonepick.domain.mail.service;

import com.hoshogi.onlyonepick.domain.mail.dto.request.SendCodeRequest;
import com.hoshogi.onlyonepick.domain.mail.dto.request.VerifyCodeRequest;
import com.hoshogi.onlyonepick.domain.mail.entity.Mail;
import com.hoshogi.onlyonepick.domain.mail.repository.MailRedisRepository;
import com.hoshogi.onlyonepick.domain.member.service.MemberService;
import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.infra.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MemberService memberService;
    private final EmailService emailService;
    private final MailRedisRepository mailRedisRepository;

    private final String MAIL_SUBJECT = "Only One Pick 이메일 인증 번호";
    private final Long TIME_TO_LIVE = 10 * 60L;
    private final Long AUTH_CODE_LENGTH = 6L;

    @Override
    @Transactional
    public void sendAuthCodeToEmail(SendCodeRequest request) {
        memberService.verifyMemberIsDuplicated(request.getEmail());
        String authCode = createAuthCode();
        emailService.sendEmail(request.getEmail(), MAIL_SUBJECT, authCode);
        mailRedisRepository.save(Mail.create(request.getEmail(), authCode, TIME_TO_LIVE));
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyAuthCode(VerifyCodeRequest request) {
        memberService.verifyMemberIsDuplicated(request.getEmail());
        Mail mail = mailRedisRepository.findById(request.getEmail())
                .orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_AUTH_CODE));
        mail.verifyAuthCode(request.getCode());
    }

    private String createAuthCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder authCode = new StringBuilder();
            for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
                authCode.append(random.nextInt(10));
            }
            return authCode.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException(ErrorCode._BAD_REQUEST);
        }
    }
}

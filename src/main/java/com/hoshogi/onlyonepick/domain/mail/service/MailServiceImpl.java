package com.hoshogi.onlyonepick.domain.mail.service;

import com.hoshogi.onlyonepick.domain.mail.dto.request.SendCodeRequest;
import com.hoshogi.onlyonepick.domain.mail.dto.request.VerifyCodeRequest;
import com.hoshogi.onlyonepick.domain.mail.entity.Mail;
import com.hoshogi.onlyonepick.domain.mail.repository.MailRedisRepository;
import com.hoshogi.onlyonepick.domain.member.repository.MemberRepository;
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

    private final EmailService emailService;
    private final MailRedisRepository mailRedisRepository;
    private final MemberRepository memberRepository;

    private final Long TIME_TO_LIVE = 10 * 60L;

    @Override
    @Transactional
    public void sendAuthCodeToEmail(SendCodeRequest request) {
        String subject = "Only One Pick 이메일 인증 번호";
        String authCode = createAuthCode();
        emailService.sendEmail(request.getEmail(), subject, authCode);
        Mail mail = Mail.builder()
                        .email(request.getEmail())
                        .authCode(authCode)
                        .ttl(TIME_TO_LIVE)
                        .build();
        mailRedisRepository.save(mail);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyAuthCode(VerifyCodeRequest request) {
        Mail mail = mailRedisRepository.findById(request.getEmail())
                .orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_AUTH_CODE));
        mail.verifyAuthCode(request.getCode());
        verifyMemberIsDuplicated(request.getEmail());
    }

    private String createAuthCode() {
        int length = 6;

        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder authCode = new StringBuilder();
            for (int i = 0; i < length; i++) {
                authCode.append(random.nextInt(10));
            }
            return authCode.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException(ErrorCode._BAD_REQUEST);
        }
    }

    private void verifyMemberIsDuplicated(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorCode.DUPLICATE_MEMBER);
        }
    }
}

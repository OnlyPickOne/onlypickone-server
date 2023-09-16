package com.hoshogi.onlyonepick.infra.email;

import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = createEmailForm(to, subject, text);
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (RuntimeException e) {
            throw new BadRequestException(ErrorCode.UNABLE_TO_SEND_EMAIL);
        }
    }
    private SimpleMailMessage createEmailForm(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        return simpleMailMessage;
    }
}

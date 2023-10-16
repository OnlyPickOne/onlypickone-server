package com.hoshogi.onlyonepick.domain.mail.entity;

import com.hoshogi.onlyonepick.global.error.ErrorCode;
import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Column;

@RedisHash("mail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mail {

    @Id
    private String email;
    private String authCode;
    @TimeToLive
    private Long ttl;

    @Builder
    public Mail(String authCode, String email, Long ttl) {
        this.authCode = authCode;
        this.email = email;
        this.ttl = ttl;
    }

    public static Mail create(String email, String authCode, Long ttl) {
        return Mail.builder()
                .email(email)
                .authCode(authCode)
                .ttl(ttl)
                .build();
    }

    public void verifyAuthCode(String authCode) {
        if (!this.authCode.equals(authCode)) {
            throw new BadRequestException(ErrorCode.INVALID_AUTH_CODE);
        }
    }
}

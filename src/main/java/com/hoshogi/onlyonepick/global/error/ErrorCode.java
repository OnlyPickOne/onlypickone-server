package com.hoshogi.onlyonepick.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /** Common */
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "C000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "C001", "잘못된 요청 입니다."),

    /** Version */
    VERSION_NOT_FOUND(BAD_REQUEST, "V000", "버전 정보가 없습니다."),

    /** Member */
    DUPLICATE_MEMBER(BAD_REQUEST, "AUTH000", "이미 존재하는 E-mail 입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

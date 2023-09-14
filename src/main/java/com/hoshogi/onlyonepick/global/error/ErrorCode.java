package com.hoshogi.onlyonepick.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    /** Common */
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "C000", ""),
    _BAD_REQUEST(BAD_REQUEST, "C001", "잘못된 요청 입니다.");

    private HttpStatus httpStatus;
    private String code;
    private String message;
}

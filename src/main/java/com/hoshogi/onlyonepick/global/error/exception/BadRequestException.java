package com.hoshogi.onlyonepick.global.error.exception;

import com.hoshogi.onlyonepick.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends BusinessException {

    public BadRequestException() {
        super(ErrorCode._BAD_REQUEST, ErrorCode._BAD_REQUEST.getMessage());
    }

    public BadRequestException(String message) {
        super(ErrorCode._BAD_REQUEST, message);
    }

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }
}

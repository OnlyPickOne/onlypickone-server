package com.hoshogi.onlyonepick.global.error.exception;

import com.hoshogi.onlyonepick.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends BusinessException {

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN_USER, ErrorCode.FORBIDDEN_USER.getMessage());
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    public ForbiddenException(String message) {
        super(ErrorCode.FORBIDDEN_USER, message);
    }
}

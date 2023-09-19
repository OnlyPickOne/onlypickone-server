package com.hoshogi.onlyonepick.global.error.exception;

import com.hoshogi.onlyonepick.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends BusinessException {

    public ForbiddenException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS, ErrorCode.UNAUTHORIZED_ACCESS.getMessage());
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    public ForbiddenException(String message) {
        super(ErrorCode.UNAUTHORIZED_ACCESS, message);
    }
}

package com.hoshogi.onlyonepick.global.error.exception;

import com.hoshogi.onlyonepick.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS, ErrorCode.UNAUTHORIZED_ACCESS.getMessage());
    }

    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED_ACCESS, message);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }
}

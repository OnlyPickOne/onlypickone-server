package com.hoshogi.onlyonepick.global.error.exception;

import com.hoshogi.onlyonepick.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
}

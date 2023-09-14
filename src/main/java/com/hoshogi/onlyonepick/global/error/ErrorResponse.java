package com.hoshogi.onlyonepick.global.error;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorResponse {

    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private final boolean isSuccess = false;
    private String code;
    private int status;
    private String message;

    @Builder
    public ErrorResponse(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}

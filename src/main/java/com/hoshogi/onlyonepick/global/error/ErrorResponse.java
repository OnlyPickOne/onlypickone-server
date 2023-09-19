package com.hoshogi.onlyonepick.global.error;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
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
                .code(errorCode.getCode())
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }

    public static JSONObject jsonOf(ErrorCode errorCode) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        jsonObject.put("isSuccess", false);
        jsonObject.put("code", errorCode.getCode());
        jsonObject.put("status", errorCode.getHttpStatus().value());
        jsonObject.put("message", errorCode.getMessage());
        return jsonObject;
    }
}

package com.hoshogi.onlyonepick.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    private Boolean isSuccess;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @Builder
    public ApiResponse(int status, String code, Boolean isSuccess, String message, T data) {
        this.status = status;
        this.code = code;
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> onSuccess(HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .status(httpStatus.value())
                .code(null)
                .isSuccess(true)
                .message(ResponseMessage.SUCCESS.getActualMessage())
                .data(null)
                .build();

    }

    public static <T> ApiResponse<T> onSuccess(HttpStatus httpStatus, T data) {
        return ApiResponse.<T>builder()
                .status(httpStatus.value())
                .code(null)
                .isSuccess(true)
                .message(ResponseMessage.SUCCESS.getActualMessage())
                .data(data)
                .build();

    }
}

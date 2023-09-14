package com.hoshogi.onlyonepick.global.error;

import com.hoshogi.onlyonepick.global.error.exception.BadRequestException;
import com.hoshogi.onlyonepick.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error("handleBusinessException", e);
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode._INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }
}

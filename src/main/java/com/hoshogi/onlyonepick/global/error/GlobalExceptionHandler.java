package com.hoshogi.onlyonepick.global.error;

import com.amazonaws.AmazonServiceException;
import com.hoshogi.onlyonepick.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.hoshogi.onlyonepick.global.error.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(AmazonServiceException.class)
    protected ResponseEntity<ErrorResponse> handleAmazonServiceException(AmazonServiceException e) {
        log.error("AmazonServiceException", e);
        return new ResponseEntity<>(ErrorResponse.of(S3_CONNECTION_ERROR), S3_CONNECTION_ERROR.getHttpStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        log.error("BadCredentialsException", e);
        return new ResponseEntity<>(ErrorResponse.of(LOGIN_FAILED), LOGIN_FAILED.getHttpStatus());
    }
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error("BusinessException", e);
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        return new ResponseEntity<>(ErrorResponse.of(_INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }
}

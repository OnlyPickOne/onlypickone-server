package com.hoshogi.onlyonepick.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /** Common */
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "C000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "C001", "잘못된 요청 입니다."),

    /** Auth */
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "AUTH000", "권한이 없습니다."),
    FORBIDDEN_USER(FORBIDDEN, "AUTH001", "권한이 없는 유저 입니다"),
    INVALID_TOKEN(BAD_REQUEST, "AUTH002", "유효하지 않은 토큰 입니다"),
    LOGIN_FAILED(UNAUTHORIZED, "AUTH003", "로그인에 실패했습니다"),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "AUTH004", "권한 정보가 없는 토큰 입니다"),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "AUTH005", "Refresh Token이 유효하지 않습니다"),
    REFRESH_TOKEN_NOT_FOUND(BAD_REQUEST, "AUTH006", "로그아웃 된 사용자입니다"),

    /** Version */
    VERSION_NOT_FOUND(BAD_REQUEST, "V000", "버전 정보가 없습니다."),

    /** Mail */
    UNABLE_TO_SEND_EMAIL(BAD_REQUEST, "MAIL000", "메일 전송을 할 수 없습니다."),
    NO_SUCH_ALGORITHM(BAD_REQUEST, "MAIL001", "인증 번호를 생성할 수 없습니다."),
    INVALID_AUTH_CODE(BAD_REQUEST, "MAIL002", "유효하지 않은 인증번호 입니다."),

    /** Member */
    DUPLICATE_MEMBER(BAD_REQUEST, "MEMBER000", "이미 존재하는 E-mail 입니다."),
    MEMBER_NOT_FOUND(BAD_REQUEST, "MEMBER001", "해당 회원이 존재하지 않습니다."),

    /** Game */
    UNSUPPORTED_IMAGE_EXTENSION(BAD_REQUEST, "GAME000", "지원하지 않는 확장자 입니다."),
    GAME_NOT_FOUND(BAD_REQUEST, "GAME001", "해당 게임이 존재하지 않습니다."),

    /** Item */
    ITEM_NOT_FOUND(BAD_REQUEST, "ITEM000", "해당 아이템이 존재하지 않습니다."),

    /** Like */
    DUPLICATE_LIKE(BAD_REQUEST, "LIKE000", "이미 존재하는 좋아요 입니다."),
    LIKE_NOT_FOUND(BAD_REQUEST, "LIKE001", "해당 좋아요가 존재하지 않습니다."),

    /** Report */
    DUPLICATE_REPORT(BAD_REQUEST, "REPORT000", "이미 존재하는 신고 입니다."),

    /** Notice */
    NOTICE_NOT_FOUND(BAD_REQUEST, "NOTICE000", "해당 공지사항이 존재하지 않습니다."),

    /** S3 */
    S3_SERVER_ERROR(BAD_REQUEST, "S3000", "Amazon S3가 처리할 수 없는 요청입니다."),
    S3_CONNECTION_ERROR(BAD_REQUEST, "S3001", "Amazon S3에 연결할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

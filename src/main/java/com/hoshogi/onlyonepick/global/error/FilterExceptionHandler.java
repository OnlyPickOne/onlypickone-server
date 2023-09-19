package com.hoshogi.onlyonepick.global.error;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterExceptionHandler {

    public static void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().print(ErrorResponse.jsonOf(errorCode));
    }
}

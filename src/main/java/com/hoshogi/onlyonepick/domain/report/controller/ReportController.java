package com.hoshogi.onlyonepick.domain.report.controller;

import com.hoshogi.onlyonepick.domain.report.dto.request.ReportGameRequest;
import com.hoshogi.onlyonepick.domain.report.service.ReportService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ApiResponse<?> reportGame(@RequestBody ReportGameRequest request) {
        reportService.reportGame(request);
        return ApiResponse.onSuccess(OK);
    }
}

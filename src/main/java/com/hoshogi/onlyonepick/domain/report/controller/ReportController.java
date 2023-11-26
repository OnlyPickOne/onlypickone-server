package com.hoshogi.onlyonepick.domain.report.controller;

import com.hoshogi.onlyonepick.domain.report.service.ReportService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games/{game-id}/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ApiResponse<?> reportGame(@PathVariable("game-id") Long gameId) {
        reportService.reportGame(gameId);
        return ApiResponse.onSuccess(OK);
    }
}

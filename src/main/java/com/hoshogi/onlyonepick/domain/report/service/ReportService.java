package com.hoshogi.onlyonepick.domain.report.service;

import com.hoshogi.onlyonepick.domain.report.dto.request.ReportGameRequest;

public interface ReportService {

    void reportGame(ReportGameRequest request);
}

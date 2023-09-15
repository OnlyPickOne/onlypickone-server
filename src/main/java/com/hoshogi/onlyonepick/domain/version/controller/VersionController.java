package com.hoshogi.onlyonepick.domain.version.controller;

import com.hoshogi.onlyonepick.domain.version.dto.request.VersionRequest;
import com.hoshogi.onlyonepick.domain.version.dto.response.VersionResponse;
import com.hoshogi.onlyonepick.domain.version.service.VersionService;
import com.hoshogi.onlyonepick.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/versions")
public class VersionController {

    private final VersionService versionService;

    @GetMapping
    ApiResponse<VersionResponse> findVersion() {
        return ApiResponse.onSuccess(OK, versionService.findVersion());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    ApiResponse<?> createVersion(@RequestBody VersionRequest request) {
        versionService.saveVersion(request);
        return ApiResponse.onSuccess(CREATED);
    }
}

package com.hoshogi.onlyonepick.domain.notice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hoshogi.onlyonepick.domain.notice.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
public class NoticeResponse {

    private Long id;
    private String title;
    @JsonInclude(NON_NULL)
    private String content;
    private Long viewCount;
    private LocalDateTime createdAt;

    @Builder
    public NoticeResponse(Long id, String title, String content, Long viewCount, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }

    public static NoticeResponse of(Notice notice, boolean isContentIncluded) {
        String content = null;

        if (isContentIncluded) {
            content = notice.getContent();
        }
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(content)
                .viewCount(notice.getViewCount())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}

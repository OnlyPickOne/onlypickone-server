package com.hoshogi.onlyonepick.domain.notice.dto.request;

import com.hoshogi.onlyonepick.domain.member.entity.Member;
import com.hoshogi.onlyonepick.domain.notice.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateNoticeRequest {

    private String title;
    private String content;

    public Notice toEntity(Member member) {
        return Notice.create(title, content, member);
    }
}

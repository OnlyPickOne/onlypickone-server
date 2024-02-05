package com.hoshogi.onlyonepick.domain.notice.entity;

import com.hoshogi.onlyonepick.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE notice SET is_deleted = true WHERE notice_id = ?")
@Where(clause = "is_deleted = false")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1) not null default 0")
    private Boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Notice(Long id, String title, String content, Long viewCount, Boolean isDeleted, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.isDeleted = isDeleted;
        this.member = member;
    }

    public static Notice create(String title, String content, Member member) {
        return Notice.builder()
                .title(title)
                .content(content)
                .viewCount(0L)
                .isDeleted(false)
                .member(member)
                .build();
    }

    public void increaseViewCount() {
         viewCount++;
    }
}

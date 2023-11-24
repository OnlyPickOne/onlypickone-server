package com.hoshogi.onlyonepick.domain.member.entity;

import com.hoshogi.onlyonepick.global.common.entity.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE member_id = ?")
@Where(clause = "is_deleted = false")
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 320)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(10) not null default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1) not null default 0")
    private Boolean isDeleted;

    @Builder
    public Member(Long id, String email, String password, Authority authority, Boolean isDeleted) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.isDeleted = isDeleted;
    }

    public Boolean isAdmin() {
        if (authority == Authority.ROLE_ADMIN) {
            return true;
        }
        return false;
    }
}

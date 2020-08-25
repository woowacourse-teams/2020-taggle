package com.woowacourse.taggle.user.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.woowacourse.taggle.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String nickName;

    @Email
    @NotEmpty
    @Column(nullable = false)
    private String email;

    @Email
    @NotEmpty
    @Column(nullable = false)
    private String notificationEmail;

    @Pattern(regexp = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])+$",
            message = "휴대폰 번호의 형식이 올바르지 않습니다.")
    @Size(min = 7)
    private String phoneNumber;

    @URL
    @NotEmpty
    @Column(nullable = false, columnDefinition = "TEXT")
    private String picture;

    @Column(nullable = false)
    private Boolean isNotice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private LocalDateTime signOutDate;

    @Builder
    public User(final Long id, final String nickName, final String email, final String phoneNumber,
            final String picture,
            final Role role) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.notificationEmail = email;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
        this.isNotice = false;
        this.role = role;
    }
}

package com.wooacourse.taggle.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    private String kakaoId;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreatedDate
    private LocalDateTime signUpDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    private LocalDateTime signOutDate;

    @Builder
    public User(String nickName, String email, String kakaoId, String phoneNumber,
            Role role) {
        this.nickName = nickName;
        this.email = email;
        this.kakaoId = kakaoId;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}

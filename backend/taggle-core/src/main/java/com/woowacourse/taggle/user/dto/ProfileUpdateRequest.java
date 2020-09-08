package com.woowacourse.taggle.user.dto;

import javax.validation.constraints.Email;

import com.woowacourse.taggle.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ProfileUpdateRequest {

    @Email
    private String notificationEmail;

    private Boolean notificationEnabled;

    public User toEntity() {
        return User.builder().notificationEmail(notificationEmail).notificationEnabled(notificationEnabled).build();
    }
}

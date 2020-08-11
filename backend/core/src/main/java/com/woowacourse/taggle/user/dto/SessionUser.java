package com.woowacourse.taggle.user.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String nickName;

    @Email
    private String email;

    private String phoneNumber;

    @URL
    private String picture;

    private Role role;

    public SessionUser(final User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.picture = user.getPicture();
        this.role = user.getRole();
    }

}

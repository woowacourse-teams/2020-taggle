package com.woowacourse.taggle.security.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

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

    @URL
    private String picture;

    public SessionUser(final User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

package com.woowacourse.taggle.security.dto;

import java.io.Serializable;

import com.woowacourse.taggle.user.domain.User;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
    private String nickName;
    private String email;
    private String picture;

    public SessionUser(final User user) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

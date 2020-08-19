package com.woowacourse.taggle.tag.domain;

import org.junit.jupiter.api.BeforeEach;

import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;

public class DomainCommonUser {

    protected User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("tigger@aroundthirty.com")
                .nickName("tigger")
                .role(Role.USER)
                .picture("https://www.naver.com/")
                .build();
    }
}

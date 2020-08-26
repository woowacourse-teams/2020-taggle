package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.user.dto.SessionUser;

public class UserAcceptanceTest extends AcceptanceTest {

    @Transactional
    @Test
    void manageUser() {
        // 사용자 정보를 가져온다.
        final SessionUser user = getUserInfo();

        assertThat(user.getNickName()).isEqualTo("tigger");
        assertThat(user.getEmail()).isEqualTo("tigger@aroundthirty.com");
        assertThat(user.getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(user.getPicture()).isEqualTo("https://www.github.com");
        assertThat(user.getNotificationEmail()).isEqualTo("tigger@aroundthirty.com");
        assertThat(user.getNotificationEnabled()).isFalse();
    }

    private SessionUser getUserInfo() {
        return get("/api/v1/users/user-info", SessionUser.class);
    }
}

package com.woowacourse.taggle.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.taggle.user.dto.SessionUser;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;

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

    private void updateNotificationEmail(final String email) {
        final Map<String, Object> request = new HashMap<>();
        request.put("notificationEmail", email);

        put("/api/v1/users/notification-email", request);
    }

    private void updateNotificationEnabled(final Boolean enabled) {
        final Map<String, Object> request = new HashMap<>();
        request.put("notificationEnabled", enabled);

        put("/api/v1/users/notification-enabled", request);
    }

    private void removeUser() {
        delete("/api/v1/users");
    }

    public ExtractableResponse<MockMvcResponse> getUserInfoExtractableResponse() {
        return getExtractableResponse("/api/v1/users/user-info");
    }
}

package com.woowacourse.taggle.user.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.UserSetup;
import com.woowacourse.taggle.user.controller.docs.UserDocumentation;
import com.woowacourse.taggle.user.domain.User;

public class UserControllerTest extends ControllerTest {

    @Autowired
    private UserSetup userSetup;

    private User user;

    @BeforeEach
    void setUp() {
        user = userSetup.save();
    }

    @DisplayName("getUserInfo: 사용자 정보를 가져온다.")
    @Test
    void getUserInfo() throws Exception {
        read(user, "/api/v1/users/user-info", jsonPath("$.id", is(user.getId().intValue())))
                .andDo(UserDocumentation.getUserInfo());
    }

    @DisplayName("updateNotificationEmail: 사용자 알림 이메일을 수정한다.")
    @Test
    void updateNotificationEmail() throws Exception {
        updateByJsonParams(user, "/api/v1/users/notification-email", "{ \"notificationEmail\": \"tigger@kakao.com\" }")
                .andDo(UserDocumentation.updateNotificationEmail());
    }

    @DisplayName("updateNotificationEnabled: 사용자 알림 설정을 수정한다.")
    @Test
    void updateNotificationEnabled() throws Exception {
        updateByJsonParams(user, "/api/v1/users/notification-enabled", "{ \"notificationEnabled\": \"True\" }")
                .andDo(UserDocumentation.updateNotificationEnabled());
    }

    @DisplayName("removeUser: 카테고리 하나를 제거한다.")
    @Test
    void removeUser() throws Exception {
        remove(user, "/api/v1/users")
                .andDo(UserDocumentation.removeUser());
    }
}

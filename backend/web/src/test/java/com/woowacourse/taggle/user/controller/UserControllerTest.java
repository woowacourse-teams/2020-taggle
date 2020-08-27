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

    @DisplayName("getUserOfMine: 사용자 정보를 가져온다.")
    @Test
    void getUserOfMine() throws Exception {
        read(user, "/api/v1/users/me", jsonPath("$.id", is(user.getId().intValue())))
                .andDo(UserDocumentation.getUserOfMine());
    }

    @DisplayName("updateNotificationEmail: 사용자 알림 이메일을 수정한다.")
    @Test
    void updateProfile() throws Exception {
        updateByJsonParams(user, "/api/v1/users/me",
                "{ \"notificationEmail\": \"tigger@kakao.com\",\"notificationEnabled\": \"True\"}")
                .andDo(UserDocumentation.updateProfile());
    }

    @DisplayName("removeUserOfMine: 회원 탈퇴를 한다.")
    @Test
    void removeUserOfMine() throws Exception {
        remove(user, "/api/v1/users/me")
                .andDo(UserDocumentation.removeUser());
    }
}

package kr.taggle.user.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("hello@taggle.kr")
                .nickName("taggle")
                .notificationEmail("hello@taggle.kr")
                .notificationEnabled(true)
                .phoneNumber("010-1234-5678")
                .picture("https://image.jpg")
                .role(Role.USER)
                .build();
    }

    @DisplayName("updateNotificationEmail: 알림 이메일 업데이트")
    @Test
    void updateNotificationEmail() {
        // given
        final User updateUser = User.builder()
                .notificationEmail("change@taggle.kr")
                .build();

        // when
        user.update(updateUser);

        // then
        assertThat(user.getNotificationEmail()).isEqualTo(updateUser.getNotificationEmail());
    }

    @DisplayName("updateNotificationEnabled: 알림 설정 업데이트")
    @Test
    void updateNotificationEnabled() {
        // given
        final User updateUser = User.builder()
                .notificationEnabled(false)
                .build();

        // when
        user.update(updateUser);

        // then
        assertThat(user.getNotificationEnabled()).isEqualTo(updateUser.getNotificationEnabled());
    }
}

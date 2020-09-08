package kr.taggle.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.taggle.JpaTestConfiguration;
import kr.taggle.fixture.UserFixture;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.ProfileUpdateRequest;
import kr.taggle.user.exception.UserNotFoundException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JpaTestConfiguration.class)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserFixture.DEFAULT_USER;
    }

    @DisplayName("save: 유저 저장 확인 테스트")
    @Test
    void save() {
        // when
        final User actual = userService.save(user);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("updateProfile_email: 회원 정보 수정(이메일) 테스트")
    @Test
    void updateProfile_email() {
        // given
        User actual = userService.save(user);

        // when
        final ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest("tigger@taggle.kr", null);
        userService.updateProfile(actual.getId(), profileUpdateRequest);
        actual = userService.findById(actual.getId());

        // then
        assertThat(actual.getNotificationEmail()).isEqualTo("tigger@taggle.kr");
    }

    @DisplayName("updateProfile_enabled: 회원 정보 수정(알림 설정) 테스트")
    @Test
    void updateProfile_enabled() {
        // given
        User actual = userService.save(user);

        // when
        final ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(null, true);
        userService.updateProfile(actual.getId(), profileUpdateRequest);
        actual = userService.findById(actual.getId());

        // then
        assertThat(actual.getNotificationEnabled()).isTrue();
    }

    @DisplayName("removeUser: 회원 탈퇴 테스트")
    @Test
    void removeUser() {
        // given
        final User newUser = userService.save(user);

        // when
        userService.removeUser(newUser.getId());

        // then
        assertThatThrownBy(() -> userService.findById(newUser.getId()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("사용자가 존재하지 않습니다.");
    }

    @DisplayName("findByEmail: 이메일로 유저 찾기 테스트")
    @Test
    void findByEmail() {
        // given
        final User newUser = userService.save(user);

        // when
        final User actual = userService.findById(newUser.getId());

        // then
        assertThat(actual.getId()).isEqualTo(newUser.getId());
        assertThat(actual.getEmail()).isEqualTo("jordyLover@kakao.com");
        assertThat(actual.getNickName()).isEqualTo("tigger");
        assertThat(actual.getPicture()).isEqualTo("https://www.naver.com/");
    }
}

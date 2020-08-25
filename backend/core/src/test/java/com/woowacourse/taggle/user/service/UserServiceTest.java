package com.woowacourse.taggle.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.fixture.UserFixture;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.exception.UserNotFoundException;

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

    @DisplayName("findByEmail: 이메일로 유저 찾기 테스트")
    @Test
    void findByEmail() {
        // given
        final User newUser = userService.save(user);

        // when
        final User actual = userService.findById(newUser.getId());

        // then
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getEmail()).isEqualTo("jordyLover@kakao.com");
        assertThat(actual.getNickName()).isEqualTo("tigger");
        assertThat(actual.getPicture()).isEqualTo("https://www.naver.com/");
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
}

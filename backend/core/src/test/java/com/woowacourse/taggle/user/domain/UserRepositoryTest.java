package com.woowacourse.taggle.user.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.woowacourse.taggle.JpaTestConfiguration;
import com.woowacourse.taggle.fixture.UserFixture;

@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserFixture.DEFAULT_USER;
    }

    @DisplayName("save: 유저 저장 확인 테스트")
    @Test
    void save() {
        // when
        final User actual = userRepository.save(user);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("findByEmail: 이메일로 유저 찾기 테스트")
    @Test
    void findByEmail() {
        // when
        User newUser = userRepository.save(user);
        final Optional<User> actual = userRepository.findById(newUser.getId());

        // then
        assertThat(actual).isNotEmpty();
    }
}

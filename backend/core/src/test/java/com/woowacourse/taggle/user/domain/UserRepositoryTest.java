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

@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("태글", "taggle@gmail.com", "taggle", "010-0000-0000", Role.USER);
    }

    @DisplayName("save(): 유저 저장 확인 테스트")
    @Test
    void save() {
        // when
        final User actual = userRepository.save(user);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("findByEmail(): 이메일로 유저 찾기 테스트")
    @Test
    void findByEmail() {
        // when
        userRepository.save(user);
        final Optional<User> actual = userRepository.findByEmail(user.getEmail());

        // then
        assertThat(actual).isNotEmpty();
    }
}

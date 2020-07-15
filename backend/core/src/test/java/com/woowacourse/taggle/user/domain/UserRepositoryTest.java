package com.woowacourse.taggle.user.domain;

import static org.assertj.core.api.Assertions.*;

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

    @DisplayName("save(): 유저가 저장되는지 확인하는 테스트")
    @Test
    void save() {
        final User user = new User("태글", "taggle@gmail.com", "taggle", "010-0000-0000", Role.USER);
        final User actual = userRepository.save(user);
        assertThat(actual).isNotNull();
    }
}

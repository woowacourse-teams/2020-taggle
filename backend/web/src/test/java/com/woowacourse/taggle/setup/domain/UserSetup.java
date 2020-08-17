package com.woowacourse.taggle.setup.domain;

import org.springframework.stereotype.Component;

import com.woowacourse.taggle.user.domain.Role;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSetup {

    private final UserRepository userRepository;

    public User save() {
        final User user = User.builder()
                .id(1L)
                .email("jordyLover@kakao.com")
                .nickName("tigger")
                .role(Role.USER)
                .picture("https://www.naver.com/")
                .build();
        return userRepository.save(user);
    }
}

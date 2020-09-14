package kr.taggle.setup.domain;

import org.springframework.stereotype.Component;

import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;
import kr.taggle.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSetup {

    private final UserRepository userRepository;

    public User save() {
        final User user = User.builder()
                .id(1L)
                .email("jordyLover@kakao.com")
                .notificationEmail("jordyLover@kakao.com")
                .nickName("tigger")
                .role(Role.USER)
                .picture("https://www.naver.com/")
                .notificationEnabled(false)
                .build();
        return userRepository.save(user);
    }
}

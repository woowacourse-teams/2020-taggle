package kr.taggle.fixture;

import kr.taggle.user.domain.Role;
import kr.taggle.user.domain.User;

public class UserFixture {

    public static final User DEFAULT_USER = User.builder()
            .id(1L)
            .email("jordyLover@kakao.com")
            .notificationEmail("jordyLover@kakao.com")
            .nickName("tigger")
            .role(Role.USER)
            .picture("https://www.naver.com/")
            .notificationEnabled(false)
            .build();
}

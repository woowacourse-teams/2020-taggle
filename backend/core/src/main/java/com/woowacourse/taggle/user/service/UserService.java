package com.woowacourse.taggle.user.service;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.domain.UserRepository;
import com.woowacourse.taggle.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User save(final User user) {
        return userRepository.save(user);
    }

    public User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다. User Id: " + id));
    }

    public void removeUser(final Long id) {
        userRepository.deleteById(id);
    }
}

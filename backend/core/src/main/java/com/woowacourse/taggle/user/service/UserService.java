package com.woowacourse.taggle.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.CategoryRepository;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.tag.domain.TagRepository;
import com.woowacourse.taggle.user.domain.User;
import com.woowacourse.taggle.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    public User save(final User user) {
        final User savedUser = userRepository.save(user);
        final Category category = categoryRepository.save(new Category("Uncategoried", user));
        final Tag tag = tagRepository.save(new Tag("Untagged", category));
        category.add(tag);

        return savedUser;
    }

    public Optional<User> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }
}

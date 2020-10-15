package kr.taggle.setup.domain;

import org.springframework.stereotype.Component;

import kr.taggle.tag.domain.Tag;
import kr.taggle.tag.domain.TagRepository;
import kr.taggle.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagSetup {

    private final TagRepository tagRepository;

    public Tag save(final User user) {
        return tagRepository.save(Tag.builder()
                .name("태글")
                .user(user)
                .build());
    }
}

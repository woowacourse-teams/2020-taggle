package com.woowacourse.taggle.bookmark.domain;

import java.util.ArrayList;
import java.util.List;

import com.woowacourse.taggle.bookmark.exception.AlreadyExistTagException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Tags {
    // null 체크
    private List<Tag> tags;

    public static Tags empty() {
        return new Tags(new ArrayList<>());
    }

    public void addTag(final Tag tag) {
        validateTagExist(tag);
        tags.add(tag);
    }

    private void validateTagExist(Tag tag) {
        boolean hasSameTag = tags.stream()
                .anyMatch(existTag -> existTag.isSameName(tag));
        if (hasSameTag) {
            throw new AlreadyExistTagException("이미 존재하는 태그입니다.\n"
                    + "tag:" + tag);
        }
    }
}

package com.woowacourse.taggle.bookmark.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.woowacourse.taggle.bookmark.exception.EmptyValueException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Tag(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        String trimmedName = name.trim();
        if (trimmedName.length() == 0) {
            throw new EmptyValueException("name이 존재하지 않습니다.\n"
                    + "name: " + name);
        }
    }

    public boolean isSameName(final Tag tag) {
        return this.name.equals(tag.getName());
    }
}


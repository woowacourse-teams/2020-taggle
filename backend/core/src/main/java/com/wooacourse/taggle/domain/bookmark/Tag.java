package com.wooacourse.taggle.domain.bookmark;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.wooacourse.taggle.domain.bookmark.exception.EmptyValueException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Tag {
    @Id
    private Long id;

    @Column
    private String name;

    public Tag(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        String trimmedName = name.trim();
        if (trimmedName.length() == 0) {
            throw new EmptyValueException("name이 존재하지 않습니다.\n"
                    + "name: " + name);
        }
    }
}


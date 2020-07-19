package com.woowacourse.taggle.tag.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.woowacourse.taggle.tag.exception.EmptyValueException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TagBookmark> bookmarks = new HashSet<>();

    public Tag(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        String trimmedName = name.trim();
        if (trimmedName.length() == 0) {
            throw new EmptyValueException("name이 존재하지 않습니다.\n"
                    + "name: " + name);
        }
    }

    public void addTagBookmark(final TagBookmark tagBookmark) {
        bookmarks.add(tagBookmark);
    }

    public void removeTagBookmark(final TagBookmark tagBookmark) {
        bookmarks.remove(tagBookmark);
    }
}

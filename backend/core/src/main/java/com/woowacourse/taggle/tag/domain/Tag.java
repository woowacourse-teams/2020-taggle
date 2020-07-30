package com.woowacourse.taggle.tag.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Tag {

    @OneToMany(mappedBy = "tag", orphanRemoval = true)
    private final Set<TagBookmark> bookmarks = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String name;

    public Tag(final String name) {
        this.name = name;
    }

    public void addTagBookmark(final TagBookmark tagBookmark) {
        bookmarks.add(tagBookmark);
    }

    public void removeTagBookmark(final TagBookmark tagBookmark) {
        bookmarks.remove(tagBookmark);
    }
}

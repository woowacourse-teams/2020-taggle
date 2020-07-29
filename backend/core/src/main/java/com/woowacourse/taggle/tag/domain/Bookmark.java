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

import org.hibernate.validator.constraints.URL;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<TagBookmark> tags = new HashSet<>();

    @URL
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Boolean isRead;

    public Bookmark(final String url) {
        this.url = url;
        this.isRead = false;
    }

    public void addTagBookmark(final TagBookmark tagBookmark) {
        tags.add(tagBookmark);
    }

    public void removeTagBookmark(final TagBookmark tagBookmark) {
        tags.remove(tagBookmark);
    }
}

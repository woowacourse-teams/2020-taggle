package com.woowacourse.taggle.bookmark.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Link link;

    @Column(nullable = false)
    private boolean isRead;

    private Tags tags;

    public Bookmark(Link link) {
        this.link = link;
        this.isRead = false;
        this.tags = Tags.empty();
    }

    public void addTag(final Tag tag) {
        tags.addTag(tag);
    }

    public boolean isSameUrl(final Bookmark bookmark) {
        return this.link.getUrl().equals(bookmark.getLink().getUrl());
    }
}

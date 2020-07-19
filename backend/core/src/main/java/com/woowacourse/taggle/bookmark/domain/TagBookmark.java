package com.woowacourse.taggle.bookmark.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Embeddable
@Getter
@EqualsAndHashCode(of = "id")
@ToString
@Entity
public class TagBookmark implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bookmark bookmark;

    public TagBookmark(final Tag tag, final Bookmark bookmark) {
        this.tag = tag;
        this.bookmark = bookmark;
    }
}

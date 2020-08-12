package com.woowacourse.taggle.tag.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.woowacourse.taggle.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "tag", orphanRemoval = true)
    private Set<TagBookmark> bookmarks = new HashSet<>();

    public Tag(final String name) {
        this.name = name;
        this.category = null;
    }

    public Tag(final String name, final User user) {
        this.name = name;
        this.category = null;
        this.user = user;
    }

    public void updateCategory(final Category category) {
        this.category = category;
    }

    public void addTagBookmark(final TagBookmark tagBookmark) {
        bookmarks.add(tagBookmark);
    }

    public void removeTagBookmark(final TagBookmark tagBookmark) {
        bookmarks.remove(tagBookmark);
    }

}

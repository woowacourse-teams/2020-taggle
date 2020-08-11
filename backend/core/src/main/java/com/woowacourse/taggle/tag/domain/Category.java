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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "category")
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Category(final String title) {
        this.title = title;
    }

    public Category(final String title, final User user) {
        this.title = title;
        this.user = user;
    }

    public void update(final Category category) {
        if (category.getTitle() != null) {
            this.title = category.getTitle();
        }
    }

    public void add(final Tag tag) {
        this.tags.add(tag);
    }

    public void remove(final Tag tag) {
        this.tags.remove(tag);
    }
}

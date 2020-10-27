package kr.taggle.bookmark.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.URL;

import kr.taggle.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
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

    @URL
    @Column(nullable = false)
    @Lob
    private String url;

    @Column
    private String title;

    @Column
    @Lob
    private String description;

    @Column
    @Lob
    private String image;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "bookmark", orphanRemoval = true)
    private Set<TagBookmark> tags = new HashSet<>();

    @Builder
    public Bookmark(final String url, final User user, final String title, final String description,
            final String image) {
        this.url = url;
        this.isRead = false;
        this.user = user;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public void addTagBookmark(final TagBookmark tagBookmark) {
        tags.add(tagBookmark);
    }

    public void removeTagBookmark(final TagBookmark tagBookmark) {
        tags.remove(tagBookmark);
    }

}

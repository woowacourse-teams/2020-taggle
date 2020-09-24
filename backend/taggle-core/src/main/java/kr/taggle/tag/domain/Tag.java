package kr.taggle.tag.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import kr.taggle.category.domain.Category;
import kr.taggle.user.domain.User;
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
    @Column(nullable = false, length = 25)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Tag(final String name, final User user) {
        this.name = name;
        this.category = null;
        this.user = user;
    }

    public Tag(final String name, final Category category) {
        this.name = name;
        this.category = category;
    }

    public void updateCategory(final Category category) {
        this.category = category;
    }

    public boolean isNotCategorized() {
        return category == null;
    }
}

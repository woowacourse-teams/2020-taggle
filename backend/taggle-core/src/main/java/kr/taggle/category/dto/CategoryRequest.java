package kr.taggle.category.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import kr.taggle.category.domain.Category;
import kr.taggle.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CategoryRequest {

    @NotEmpty
    @Size(max = 25, message = "{size.categoryRequest.title}")
    private String title;

    public Category toEntity() {
        return new Category(title);
    }

    public Category toEntityWithUser(final User user) {
        return new Category(title, user);
    }
}

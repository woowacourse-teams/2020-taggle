package kr.taggle.category.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

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
    @Length(max = 20, message = "카테고리 제목은 20자보다 클 수 없습니다.")
    private String title;

    public Category toEntity() {
        return new Category(title);
    }

    public Category toEntityWithUser(final User user) {
        return new Category(title, user);
    }
}

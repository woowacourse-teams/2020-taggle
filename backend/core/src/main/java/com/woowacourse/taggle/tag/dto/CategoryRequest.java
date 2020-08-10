package com.woowacourse.taggle.tag.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.woowacourse.taggle.tag.domain.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CategoryRequest {

    @NotEmpty
    @Length(max = 10, message = "카테고리 제목은 10자보다 클 수 없습니다.")
    private String title;

    public Category toEntity() {
        return new Category(title);
    }
}

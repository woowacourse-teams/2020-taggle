package com.woowacourse.taggle.tag.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.tag.dto.CategoryDetailResponse;
import com.woowacourse.taggle.tag.dto.CategoryRequest;
import com.woowacourse.taggle.tag.dto.CategoryResponse;
import com.woowacourse.taggle.tag.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid final CategoryRequest categoryRequest) {
        final CategoryResponse category = categoryService.createCategory(categoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/v1/categories/" + category.getId())
                .body(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDetailResponse>> findCategories() {
        return ResponseEntity.ok()
                .body(categoryService.findCategories());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable final Long id,
            @RequestBody @Valid final CategoryRequest categoryRequest) {
        categoryService.updateCategory(id, categoryRequest);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCategory(@PathVariable final Long id) {
        categoryService.removeCategory(id);

        return ResponseEntity.noContent()
                .build();
    }
}

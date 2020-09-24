package kr.taggle.category.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.taggle.category.service.CategoryService;
import kr.taggle.category.dto.CategoryRequest;
import kr.taggle.category.dto.CategoryResponse;
import kr.taggle.tag.dto.CategoryDetailResponse;
import kr.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @AuthenticationPrincipal final SessionUser user,
            @RequestBody @Valid final CategoryRequest categoryRequest) {
        final CategoryResponse category = categoryService.createCategory(user, categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", String.format("/api/v1/categories/%d", category.getId()))
                .body(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDetailResponse>> findAllCategories(
            @AuthenticationPrincipal final SessionUser user) {
        return ResponseEntity.ok()
                .body(categoryService.findAllTagsBy(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(
            @AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id,
            @RequestBody @Valid final CategoryRequest categoryRequest) {
        categoryService.updateCategory(user, id, categoryRequest);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCategory(
            @AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id) {
        categoryService.removeCategory(user, id);
        return ResponseEntity.noContent()
                .build();
    }
}

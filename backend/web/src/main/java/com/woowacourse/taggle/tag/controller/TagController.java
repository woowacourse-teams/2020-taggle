package com.woowacourse.taggle.tag.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.service.TagBookmarkService;
import com.woowacourse.taggle.tag.service.TagService;
import com.woowacourse.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
@RestController
public class TagController {

    private final TagService tagService;
    private final TagBookmarkService tagBookmarkService;

    @PostMapping
    public ResponseEntity<Void> createTag(
            @AuthenticationPrincipal final SessionUser user,
            @RequestBody @Valid final TagCreateRequest tagCreateRequest) {
        final TagResponse tag = tagService.createTag(user, tagCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/tags/" + tag.getId()))
                .build();
    }

    @PostMapping("/{tagId}/bookmarks/{bookmarkId}")
    public ResponseEntity<Void> addBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId, @PathVariable final Long bookmarkId) {
        tagBookmarkService.createTagBookmark(tagId, bookmarkId);

        return ResponseEntity.created(URI.create("/api/v1/tags/" + tagId + "/bookmarks"))
                .build();
    }

    @GetMapping("/{id}/bookmarks")
    public ResponseEntity<TagBookmarkResponse> findTagById(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id) {
        final TagBookmarkResponse tagBookmarkResponse = tagService.findTagById(id);

        return ResponseEntity.ok()
                .body(tagBookmarkResponse);
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> findTags(@AuthenticationPrincipal final SessionUser user) {
        return ResponseEntity.ok()
                .body(tagService.findTags());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id) {
        tagService.removeTag(id);

        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping("/{tagId}/categories/{categoryId}")
    public ResponseEntity<Void> updateCategoryOnTag(
            @AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId,
            @PathVariable final Long categoryId) {
        tagService.updateCategory(tagId, categoryId);

        return ResponseEntity.ok()
                .build();
    }
}

package com.woowacourse.taggle.tag.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        System.out.println("createTag 아이디 " + tag.getId());
        return ResponseEntity.created(URI.create("/api/v1/tags/" + tag.getId()))
                .build();
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagBookmarkResponse> findTagById(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId) {
        System.out.println("패스붸리어블" + tagId);
        final TagBookmarkResponse tagBookmarkResponse = tagService.findTagById(user, tagId);

        return ResponseEntity.ok()
                .body(tagBookmarkResponse);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> removeTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId) {
        tagService.removeTag(user, tagId);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/{tagId}/bookmarks/{bookmarkId}")
    public ResponseEntity<Void> addBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId,
            @PathVariable final Long bookmarkId) {
        tagBookmarkService.createTagBookmark(user, tagId, bookmarkId);

        return ResponseEntity.created(URI.create("/api/v1/tags/" + tagId + "/bookmarks" + bookmarkId))
                .build();
    }

    @DeleteMapping("/{tagId}/bookmarks/{bookmarkId}")
    public ResponseEntity<Void> removeBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId,
            @PathVariable final Long bookmarkId) {
        tagBookmarkService.removeTagBookmark(user, tagId, bookmarkId);

        return ResponseEntity.created(URI.create("/api/v1/tags/" + tagId + "/bookmarks" + bookmarkId))
                .build();
    }
}

package com.woowacourse.taggle.tag.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.tag.dto.BookmarkTagResponse;
import com.woowacourse.taggle.tag.dto.TagBookmarkResponse;
import com.woowacourse.taggle.tag.service.TagBookmarkService;
import com.woowacourse.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class TagBookmarkController {

    private final TagBookmarkService tagBookmarkService;

    @GetMapping("/bookmarks/{id}/tags")
    public ResponseEntity<BookmarkTagResponse> findTagsOfBookmark(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id) {
        final BookmarkTagResponse bookmarkTagResponse = tagBookmarkService.findTagsOfBookmark(user, id);

        return ResponseEntity.ok()
                .body(bookmarkTagResponse);
    }

    @GetMapping("/tags/{tagId}/bookmarks")
    public ResponseEntity<TagBookmarkResponse> findBookmarksOfTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId) {
        final TagBookmarkResponse tagBookmarkResponse = tagBookmarkService.findBookmarksOfTag(user, tagId);

        return ResponseEntity.ok()
                .body(tagBookmarkResponse);
    }

    @GetMapping("/tags/untagged/bookmarks")
    public ResponseEntity<TagBookmarkResponse> findBookmarksOfUntagged(
            @AuthenticationPrincipal final SessionUser user) {
        final TagBookmarkResponse tagBookmarkResponse = tagBookmarkService.findBookmarksOfUntagged(user);

        return ResponseEntity.ok()
                .body(tagBookmarkResponse);
    }

    @PostMapping("/tags/{tagId}/bookmarks/{bookmarkId}")
    public ResponseEntity<Void> addBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId,
            @PathVariable final Long bookmarkId) {
        tagBookmarkService.createTagBookmark(user, tagId, bookmarkId);

        return ResponseEntity.created(URI.create("/api/v1/tags/" + tagId + "/bookmarks/" + bookmarkId))
                .build();
    }

    @DeleteMapping("/tags/{tagId}/bookmarks/{bookmarkId}")
    public ResponseEntity<Void> removeBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId,
            @PathVariable final Long bookmarkId) {
        tagBookmarkService.removeTagBookmark(user, tagId, bookmarkId);

        return ResponseEntity.noContent()
                .build();
    }
}

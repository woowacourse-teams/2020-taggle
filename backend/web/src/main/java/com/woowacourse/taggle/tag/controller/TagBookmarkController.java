package com.woowacourse.taggle.tag.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.tag.service.TagBookmarkService;
import com.woowacourse.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class TagBookmarkController {

    private final TagBookmarkService tagBookmarkService;

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

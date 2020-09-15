package kr.taggle.bookmark.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.taggle.bookmark.service.TagBookmarkService;
import kr.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks/{bookmarkId}/tags/{tagId}")
@RestController
public class TagBookmarkController {

    private final TagBookmarkService tagBookmarkService;

    @PostMapping
    public ResponseEntity<Void> addBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long bookmarkId, @PathVariable final Long tagId) {
        tagBookmarkService.createTagBookmark(user, bookmarkId, tagId);

        return ResponseEntity.created(URI.create(String.format("/api/v1/bookmarks/%d/tags/%d", bookmarkId, tagId)))
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long bookmarkId, @PathVariable final Long tagId) {
        tagBookmarkService.removeTagBookmark(user, bookmarkId, tagId);

        return ResponseEntity.noContent()
                .build();
    }
}

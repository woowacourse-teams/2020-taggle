package kr.taggle.bookmark.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.taggle.bookmark.dto.BookmarkPageRequest;
import kr.taggle.bookmark.dto.BookmarkTagResponse;
import kr.taggle.bookmark.dto.TagBookmarkResponse;
import kr.taggle.bookmark.service.TagBookmarkService;
import kr.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class TagBookmarkController {

    private final TagBookmarkService tagBookmarkService;

    @GetMapping("/bookmarks/{id}/tags")
    public ResponseEntity<BookmarkTagResponse> findTagsByBookmarkId(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id) {
        final BookmarkTagResponse bookmarkTagResponse = tagBookmarkService.findTagsByBookmarkId(user, id);

        return ResponseEntity.ok()
                .body(bookmarkTagResponse);
    }

    @GetMapping("/tags/{tagId}/bookmarks")
    public ResponseEntity<TagBookmarkResponse> findBookmarksByTagId(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId,
            @ModelAttribute final BookmarkPageRequest bookmarkPageRequest) {
        final TagBookmarkResponse tagBookmarkResponse = tagBookmarkService.findBookmarksByTagId(user, tagId,
                bookmarkPageRequest);

        return ResponseEntity.ok()
                .body(tagBookmarkResponse);
    }

    @GetMapping("/tags/untagged/bookmarks")
    public ResponseEntity<TagBookmarkResponse> findUntaggedBookmarks(
            @AuthenticationPrincipal final SessionUser user,
            @ModelAttribute final BookmarkPageRequest bookmarkPageRequest) {
        final TagBookmarkResponse tagBookmarkResponse = tagBookmarkService.findUntaggedBookmarks(user,
                bookmarkPageRequest);

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

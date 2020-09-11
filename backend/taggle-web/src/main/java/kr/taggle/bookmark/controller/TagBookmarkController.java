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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/bookmarks/{bookmarkId}")
    public ResponseEntity<BookmarkTagResponse> findBookmarkDetail(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long bookmarkId) {
        final BookmarkTagResponse bookmarkTagResponse = tagBookmarkService.findTagsByBookmarkId(user, bookmarkId);

        return ResponseEntity.ok()
                .body(bookmarkTagResponse);
    }

    @GetMapping(value = "/bookmarks", params = "tag")
    public ResponseEntity<TagBookmarkResponse> findBookmarksByTagId(@AuthenticationPrincipal final SessionUser user,
            @RequestParam final Long tag,
            @ModelAttribute final BookmarkPageRequest bookmarkPageRequest) {
        final TagBookmarkResponse tagBookmarkResponse = tagBookmarkService.findBookmarksByTagId(user, tag,
                bookmarkPageRequest);

        return ResponseEntity.ok()
                .body(tagBookmarkResponse);
    }

    @GetMapping(value = "/bookmarks", params = "tag=none")
    public ResponseEntity<TagBookmarkResponse> findBookmarksWithUntagged(
            @AuthenticationPrincipal final SessionUser user,
            @ModelAttribute final BookmarkPageRequest bookmarkPageRequest) {
        final TagBookmarkResponse tagBookmarkResponse = tagBookmarkService.findUntaggedBookmarks(user,
                bookmarkPageRequest);

        return ResponseEntity.ok()
                .body(tagBookmarkResponse);
    }

    @PostMapping("/bookmarks/{bookmarkId}/tags/{tagId}")
    public ResponseEntity<Void> addBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long bookmarkId, @PathVariable final Long tagId) {
        tagBookmarkService.createTagBookmark(user, bookmarkId, tagId);

        return ResponseEntity.created(URI.create(String.format("/api/v1/bookmarks/%d/tags/%d", bookmarkId, tagId)))
                .build();
    }

    @DeleteMapping("/bookmarks/{bookmarkId}/tags/{tagId}")
    public ResponseEntity<Void> removeBookmarkOnTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long bookmarkId, @PathVariable final Long tagId) {
        tagBookmarkService.removeTagBookmark(user, bookmarkId, tagId);

        return ResponseEntity.noContent()
                .build();
    }
}

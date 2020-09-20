package kr.taggle.bookmark.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.taggle.bookmark.dto.BookmarkCreateRequest;
import kr.taggle.bookmark.dto.BookmarkPageRequest;
import kr.taggle.bookmark.dto.BookmarkResponse;
import kr.taggle.bookmark.dto.BookmarkDetailResponse;
import kr.taggle.bookmark.dto.TagDetailResponse;
import kr.taggle.bookmark.service.BookmarkCreateService;
import kr.taggle.bookmark.service.BookmarkService;
import kr.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkCreateService bookmarkCreateService;

    @PostMapping
    public ResponseEntity<BookmarkResponse> createBookmark(@AuthenticationPrincipal final SessionUser user,
            @RequestBody @Valid final BookmarkCreateRequest bookmarkCreateRequest) {
        final BookmarkResponse bookmark = bookmarkCreateService.createBookmark(bookmarkCreateRequest, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", String.format("/api/v1/bookmarks/%d", bookmark.getId()))
                .body(bookmark);
    }

    @GetMapping
    public ResponseEntity<List<BookmarkResponse>> findBookmarks(@AuthenticationPrincipal final SessionUser user,
            @ModelAttribute final BookmarkPageRequest bookmarkPageRequest) {
        final List<BookmarkResponse> bookmarks = bookmarkService.findBookmarks(user, bookmarkPageRequest);

        return ResponseEntity.ok()
                .body(bookmarks);
    }

    @GetMapping("/{bookmarkId}")
    public ResponseEntity<BookmarkDetailResponse> findBookmarkDetail(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long bookmarkId) {
        final BookmarkDetailResponse bookmarkDetailResponse = bookmarkService.findTagsByBookmarkId(user, bookmarkId);

        return ResponseEntity.ok()
                .body(bookmarkDetailResponse);
    }

    @GetMapping(params = "tag")
    public ResponseEntity<TagDetailResponse> findBookmarksByTagId(@AuthenticationPrincipal final SessionUser user,
            @RequestParam final Long tag,
            @ModelAttribute final BookmarkPageRequest bookmarkPageRequest) {
        final TagDetailResponse tagDetailResponse = bookmarkService.findBookmarksByTagId(user, tag,
                bookmarkPageRequest);

        return ResponseEntity.ok()
                .body(tagDetailResponse);
    }

    @GetMapping(params = "tag=none")
    public ResponseEntity<TagDetailResponse> findBookmarksWithUntagged(
            @AuthenticationPrincipal final SessionUser user,
            @ModelAttribute final BookmarkPageRequest bookmarkPageRequest) {
        final TagDetailResponse tagDetailResponse = bookmarkService.findUntaggedBookmarks(user,
                bookmarkPageRequest);

        return ResponseEntity.ok()
                .body(tagDetailResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBookmark(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id) {
        bookmarkService.removeBookmark(user, id);

        return ResponseEntity.noContent()
                .build();
    }
}

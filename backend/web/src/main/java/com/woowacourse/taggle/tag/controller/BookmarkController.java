package com.woowacourse.taggle.tag.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.dto.BookmarkTagResponse;
import com.woowacourse.taggle.tag.service.BookmarkService;
import com.woowacourse.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<BookmarkResponse> createBookmark(@AuthenticationPrincipal final SessionUser user,
            @RequestBody @Valid final BookmarkCreateRequest bookmarkCreateRequest) {
        final BookmarkResponse bookmark = bookmarkService.createBookmark(user, bookmarkCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/v1/bookmarks/" + bookmark.getId())
                .body(bookmark);
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<BookmarkTagResponse> findBookmark(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id) {
        final BookmarkTagResponse bookmarkTagResponse = bookmarkService.findBookmark(user, id);

        return ResponseEntity.ok()
                .body(bookmarkTagResponse);
    }

    @GetMapping
    public ResponseEntity<List<BookmarkResponse>> findBookmarks(@AuthenticationPrincipal final SessionUser user) {
        final List<BookmarkResponse> bookmarks = bookmarkService.findBookmarks(user);

        return ResponseEntity.ok()
                .body(bookmarks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBookmark(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long id) {
        bookmarkService.removeBookmark(user, id);

        return ResponseEntity.noContent()
                .build();
    }
}

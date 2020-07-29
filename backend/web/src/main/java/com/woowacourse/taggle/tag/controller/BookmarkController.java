package com.woowacourse.taggle.tag.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.tag.dto.BookmarkCreateRequest;
import com.woowacourse.taggle.tag.dto.BookmarkResponse;
import com.woowacourse.taggle.tag.service.BookmarkService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<Void> createBookmark(
            @RequestBody @Valid final BookmarkCreateRequest bookmarkCreateRequest) {
        final BookmarkResponse bookmark = bookmarkService.createBookmark(bookmarkCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/bookmarks/" + bookmark.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<BookmarkResponse>> findBookmarks() {
        final List<BookmarkResponse> bookmarks = bookmarkService.findBookmarks();

        return ResponseEntity.ok()
                .body(bookmarks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBookmark(@PathVariable Long id) {
        bookmarkService.removeBookmark(id);

        return ResponseEntity.noContent()
                .build();
    }
}

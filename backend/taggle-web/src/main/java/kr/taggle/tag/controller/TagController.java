package kr.taggle.tag.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.taggle.tag.dto.TagUpdateRequest;
import kr.taggle.tag.service.TagService;
import kr.taggle.tag.dto.TagCreateRequest;
import kr.taggle.tag.dto.TagResponse;
import kr.taggle.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
@RestController
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(
            @AuthenticationPrincipal final SessionUser user,
            @RequestBody @Valid final TagCreateRequest tagCreateRequest) {
        final TagResponse tag = tagService.createTag(user, tagCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", String.format("/api/v1/tags/%d", tag.getId()))
                .body(tag);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Void> updateCategoryOnTag(
            @AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId,
            @RequestBody @Valid final TagUpdateRequest tagUpdateRequest) {
        tagService.updateTag(user, tagUpdateRequest, tagId);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> removeTag(@AuthenticationPrincipal final SessionUser user,
            @PathVariable final Long tagId) {
        tagService.removeTag(user, tagId);

        return ResponseEntity.noContent()
                .build();
    }
}

package com.woowacourse.taggle.tag.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.tag.dto.TagCreateRequest;
import com.woowacourse.taggle.tag.dto.TagResponse;
import com.woowacourse.taggle.tag.service.TagService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/v1/tags")
@RestController
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Void> createTag(@RequestBody @Valid final TagCreateRequest tagCreateRequest) {
        final TagResponse tag = tagService.createTag(tagCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/tags/" + tag.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> findTags() {
        return ResponseEntity.ok()
                .body(tagService.findTags());
    }
}

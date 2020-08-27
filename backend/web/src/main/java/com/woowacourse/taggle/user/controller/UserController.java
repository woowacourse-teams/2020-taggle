package com.woowacourse.taggle.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.tag.dto.ProfileUpdateRequest;
import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<SessionUser> getUserOfMine(@AuthenticationPrincipal final SessionUser user) {
        return ResponseEntity.ok()
                .body(user);
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal final SessionUser user,
            @RequestBody @Valid final ProfileUpdateRequest profileUpdateRequest) {
        userService.updateProfile(user.getId(), profileUpdateRequest);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> removeUserOfMine(@AuthenticationPrincipal final SessionUser user) {
        userService.removeUser(user.getId());

        return ResponseEntity.noContent()
                .build();
    }
}

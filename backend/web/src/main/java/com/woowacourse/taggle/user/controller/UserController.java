package com.woowacourse.taggle.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.user.dto.SessionUser;
import com.woowacourse.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/user-info")
    public ResponseEntity<SessionUser> getUserInfo(@AuthenticationPrincipal final SessionUser user) {
        return ResponseEntity.ok()
                .body(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeUser(@AuthenticationPrincipal final SessionUser user) {
        userService.removeUser(user.getId());

        return ResponseEntity.noContent()
                .build();
    }
}

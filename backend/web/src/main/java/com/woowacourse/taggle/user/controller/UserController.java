package com.woowacourse.taggle.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowacourse.taggle.user.dto.SessionUser;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    @GetMapping("/user-info")
    public ResponseEntity<SessionUser> getUserInfo(@AuthenticationPrincipal final SessionUser user) {
        return ResponseEntity.ok()
                .body(user);
    }
}

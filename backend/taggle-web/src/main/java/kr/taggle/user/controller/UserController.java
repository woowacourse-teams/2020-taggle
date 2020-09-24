package kr.taggle.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.taggle.user.dto.ProfileUpdateRequest;
import kr.taggle.user.dto.SessionUser;
import kr.taggle.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/me")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<SessionUser> getUserOfMine(@AuthenticationPrincipal final SessionUser user) {
        return ResponseEntity.ok()
                .body(user);
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal final SessionUser user,
            @RequestBody @Valid final ProfileUpdateRequest profileUpdateRequest) {
        userService.updateProfile(user.getId(), profileUpdateRequest);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeUserOfMine(@AuthenticationPrincipal final SessionUser user) {
        userService.removeUser(user.getId());

        return ResponseEntity.noContent()
                .build();
    }
}

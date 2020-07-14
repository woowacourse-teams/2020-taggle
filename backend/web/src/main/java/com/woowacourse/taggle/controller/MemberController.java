package com.woowacourse.taggle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wooacourse.taggle.Member;

@RestController
public class MemberController {

    @GetMapping("/")
    public String test() {
        Member member = new Member("죠르디", "@@@@");
        return member.toString();
    }
}

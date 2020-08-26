package com.woowacourse.taggle.user.controller.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class UserDocumentation {

    public static RestDocumentationResultHandler getUserInfo() {
        return document("users/get/info",
                responseFields(
                        fieldWithPath("id").description("사용자 ID"),
                        fieldWithPath("nickName").description("사용자 닉네임"),
                        fieldWithPath("email").description("사용자 이메일"),
                        fieldWithPath("notificationEmail").description("사용자 알림 이메일"),
                        fieldWithPath("phoneNumber").description("사용자 전화 번호 (Nullable)").optional(),
                        fieldWithPath("picture").description("사용자 프로필 이미지"),
                        fieldWithPath("notificationEnabled").description("사용자 알림 설정"),
                        fieldWithPath("role").description("사용자의 ROLE")
                )
        );
    }

    public static RestDocumentationResultHandler updateNotificationEmail() {
        return document("users/update/notification/email",
                requestFields(
                        fieldWithPath("notificationEmail").description("사용자 알림 이메일")
                )
        );
    }

    public static RestDocumentationResultHandler updateNotificationEnabled() {
        return document("users/update/notification/enabled",
                requestFields(
                        fieldWithPath("notificationEnabled").description("사용자 알림 설정")
                )
        );
    }

    public static RestDocumentationResultHandler removeUser() {
        return document("users/delete");
    }
}

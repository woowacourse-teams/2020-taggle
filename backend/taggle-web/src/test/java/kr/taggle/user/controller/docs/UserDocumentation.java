package kr.taggle.user.controller.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserDocumentation {

    public static RestDocumentationResultHandler getUserOfMine() {
        return document("me/get",
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("사용자 ID"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("사용자 닉네임"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                        fieldWithPath("notificationEmail").type(JsonFieldType.STRING).description("사용자 알림 이메일"),
                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("사용자 전화 번호").optional(),
                        fieldWithPath("picture").type(JsonFieldType.STRING).description("사용자 프로필 이미지"),
                        fieldWithPath("notificationEnabled").type(JsonFieldType.BOOLEAN).description("사용자 알림 설정"),
                        fieldWithPath("role").type(JsonFieldType.STRING).description("사용자의 ROLE")
                )
        );
    }

    public static RestDocumentationResultHandler updateProfile() {
        return document("me/update",
                requestFields(
                        fieldWithPath("notificationEmail").type(JsonFieldType.STRING).description("사용자 알림 이메일").optional(),
                        fieldWithPath("notificationEnabled").type(JsonFieldType.BOOLEAN).description("사용자 알림 설정").optional()
                )
        );
    }

    public static RestDocumentationResultHandler removeUser() {
        return document("me/delete");
    }
}

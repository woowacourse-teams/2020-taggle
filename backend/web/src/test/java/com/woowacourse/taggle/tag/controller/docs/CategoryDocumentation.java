package com.woowacourse.taggle.tag.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class CategoryDocumentation {

    public static RestDocumentationResultHandler createCategory() {
        return document("categories/create",
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("카테고리의 제목")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 카테고리의 URI")
                )
        );
    }
}

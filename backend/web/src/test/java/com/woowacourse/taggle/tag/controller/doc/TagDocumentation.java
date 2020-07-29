package com.woowacourse.taggle.tag.controller.doc;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class TagDocumentation {
    public static RestDocumentationResultHandler createTag() {
        return document("tags/create",
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("태그 이")
                ),
                responseHeaders(
                        headerWithName("Location").description("GET 름 URL")
                )
        );
    }

    public static RestDocumentationResultHandler findTags() {
        return document("tags/get",
                responseFields(
                        fieldWithPath("[].id").description("태그 ID"),
                        fieldWithPath("[].name").description("태그 이름")
                )
        );
    }
}

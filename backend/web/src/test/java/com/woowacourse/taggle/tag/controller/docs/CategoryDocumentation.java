package com.woowacourse.taggle.tag.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultHandler;

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

    public static RestDocumentationResultHandler findCategories() {
        return document("categories/get/List",
                responseFields(
                        fieldWithPath("[].id").description("카테고리 ID"),
                        fieldWithPath("[].title").description("카테고리 제목"),
                        fieldWithPath("[].tags.[].id").description("태그 ID"),
                        fieldWithPath("[].tags.[].name").description("태그 이름")
                )
        );
    }
}

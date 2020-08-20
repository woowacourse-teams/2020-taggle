package com.woowacourse.taggle.tag.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

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
                ),
                responseFields(
                        fieldWithPath("id").description("카테고리 ID"),
                        fieldWithPath("title").description("카테고리 제목")
                )
        );
    }

    public static RestDocumentationResultHandler findCategories() {
        return document("categories/get/List",
                responseFields(
                        fieldWithPath("[].id").description("카테고리 ID (Nullable)").optional(),
                        fieldWithPath("[].title").description("카테고리 제목"),
                        fieldWithPath("[].tags").description("카테고리의 태그 (Nullable)").optional(),
                        fieldWithPath("[].tags.[].id").description("태그 ID"),
                        fieldWithPath("[].tags.[].name").description("태그 이름")
                )
        );
    }

    public static RestDocumentationResultHandler updateCategory() {
        return document("categories/update",
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("카테고리의 제목")
                )
        );
    }

    public static RestDocumentationResultHandler updateCategoryOnTag() {
        return document("categories/tags/update",
                pathParameters(
                        parameterWithName("categoryId").description("카테고리 ID"),
                        parameterWithName("tagId").description("태그 ID")
                )
        );
    }

    public static RestDocumentationResultHandler removeCategory() {
        return document("categories/delete",
                pathParameters(
                        parameterWithName("categoryId").description("카테고리 ID")
                )
        );
    }
}

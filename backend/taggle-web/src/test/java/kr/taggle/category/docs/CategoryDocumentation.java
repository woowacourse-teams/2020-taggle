package kr.taggle.category.docs;

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
                        fieldWithPath("title").type(JsonFieldType.STRING).description("카테고리 이름")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 카테고리의 URI")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("카테고리 이름")
                )
        );
    }

    public static RestDocumentationResultHandler findCategories() {
        return document("categories/get/List",
                responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("카테고리 ID").optional(),
                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("카테고리 이름"),
                        fieldWithPath("[].tags").type(JsonFieldType.ARRAY).description("카테고리의 태그 목록").optional(),
                        fieldWithPath("[].tags.[].id").type(JsonFieldType.NUMBER).description("태그 ID"),
                        fieldWithPath("[].tags.[].name").type(JsonFieldType.STRING).description("태그 이름")
                )
        );
    }

    public static RestDocumentationResultHandler updateCategory() {
        return document("categories/update",
                pathParameters(
                        parameterWithName("categoryId").description("카테고리 ID")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("카테고리 이름")
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

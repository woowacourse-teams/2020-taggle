package com.woowacourse.taggle.tag.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class TagDocumentation {
    
    public static RestDocumentationResultHandler createTag() {
        return document("tags/create",
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("태그 이름")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 태그의 URI")
                )
        );
    }

    public static RestDocumentationResultHandler findTag() {
        return document("tags/get",
                pathParameters(
                        parameterWithName("tagId").description("태그 ID")
                ),
                responseFields(
                        fieldWithPath("id").description("태그 ID"),
                        fieldWithPath("name").description("태그 이름"),
                        fieldWithPath("bookmarks").description("태그의 북마크 (Nullable)").optional(),
                        fieldWithPath("bookmarks.[].id").description("북마크 ID"),
                        fieldWithPath("bookmarks.[].url").description("북마크 URI"),
                        fieldWithPath("bookmarks.[].title").description("북마크 타이틀"),
                        fieldWithPath("bookmarks.[].description").description("북마크 설명"),
                        fieldWithPath("bookmarks.[].image").description("북마크 이미지")
                ));
    }

    public static RestDocumentationResultHandler findUntagged() {
        return document("tags/untagged/get",
                responseFields(
                        fieldWithPath("id").description("태그 ID (Null)").optional(),
                        fieldWithPath("name").description("태그 이름 (Untagged)"),
                        fieldWithPath("bookmarks.[].id").description("북마크 ID"),
                        fieldWithPath("bookmarks.[].url").description("북마크 URI"),
                        fieldWithPath("bookmarks.[].title").description("북마크 타이틀"),
                        fieldWithPath("bookmarks.[].description").description("북마크 설명"),
                        fieldWithPath("bookmarks.[].image").description("북마크 이미지")
                ));
    }

    public static RestDocumentationResultHandler removeTags() {
        return document("tags/delete",
                pathParameters(
                        parameterWithName("tagId").description("태그 ID")
                )
        );
    }
}

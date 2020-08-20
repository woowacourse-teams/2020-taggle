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
                ),
                responseFields(
                        fieldWithPath("id").description("태그 ID"),
                        fieldWithPath("name").description("태그 이름")
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

    public static RestDocumentationResultHandler addBookmarkOnTag() {
        return document("tags/bookmarks/create",
                pathParameters(
                        parameterWithName("tagId").description("태그 ID"),
                        parameterWithName("bookmarkId").description("북마크 ID")
                ),
                responseHeaders(
                        headerWithName("Location").description("태그 조회 URI")
                )
        );
    }

    public static RestDocumentationResultHandler removeBookmarkOnTag() {
        return document("tags/bookmarks/delete",
                pathParameters(
                        parameterWithName("tagId").description("태그 ID"),
                        parameterWithName("bookmarkId").description("북마크 ID")
                )
        );
    }
}

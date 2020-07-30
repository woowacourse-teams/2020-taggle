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
                        parameterWithName("id").description("태그 ID")
                ),
                responseFields(
                        fieldWithPath("id").description("태그 ID"),
                        fieldWithPath("name").description("태그 이름"),
                        fieldWithPath("bookmarks.[].id").description("북마크 ID"),
                        fieldWithPath("bookmarks.[].url").description("북마크 URI")
                ));
    }

    public static RestDocumentationResultHandler findTags() {
        return document("tags/get/list",
                responseFields(
                        fieldWithPath("[].id").description("태그 ID"),
                        fieldWithPath("[].name").description("태그 이름")
                )
        );
    }

    public static RestDocumentationResultHandler removeTags() {
        return document("tags/delete",
                pathParameters(
                        parameterWithName("id").description("태그 ID")
                )
        );
    }

    public static RestDocumentationResultHandler addBookmarkOnTag() {
        return document("tags/tagBookmark",
                pathParameters(
                        parameterWithName("tagId").description("태그 ID"),
                        parameterWithName("bookmarkId").description("북마크 ID")
                ),
                responseHeaders(
                        headerWithName("Location").description("태그 조회 URI")
                )
        );
    }
}

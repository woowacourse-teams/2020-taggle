package com.woowacourse.taggle.tag.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class BookmarkDocumentation {

    public static RestDocumentationResultHandler createBookmark() {
        return document("bookmarks/create",
                requestFields(
                        fieldWithPath("url").type(JsonFieldType.STRING).description("북마크에 추가할 URL")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 북마크의 URI")
                )
        );
    }

    public static RestDocumentationResultHandler findBookmark() {
        return document("bookmarks/get",
                pathParameters(
                        parameterWithName("id").description("북마크 ID")
                ),
                responseFields(
                        fieldWithPath("id").description("북마크 ID"),
                        fieldWithPath("url").description("북마크 URL"),
                        fieldWithPath("tags.[].id").description("북마크에 있는 태그 ID"),
                        fieldWithPath("tags.[].name").description("북마크에 있는 태그 이름")
                )
        );
    }

    public static RestDocumentationResultHandler findBookmarks() {
        return document("bookmarks/get/List",
                responseFields(
                        fieldWithPath("[].id").description("북마크 ID"),
                        fieldWithPath("[].url").description("북마크 URL")
                )
        );
    }

    public static RestDocumentationResultHandler removeBookmark() {
        return document("bookmarks/delete",
                pathParameters(
                        parameterWithName("id").description("북마크 ID")
                )
        );
    }

}

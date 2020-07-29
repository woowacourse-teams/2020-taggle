package com.woowacourse.taggle.tag.controller.doc;

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
                        headerWithName("Location").description("GET REST URL")
                )
        );
    }

    public static RestDocumentationResultHandler findBookmarks() {
        return document("bookmarks/get",
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

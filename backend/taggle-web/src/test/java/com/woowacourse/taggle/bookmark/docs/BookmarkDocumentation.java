package com.woowacourse.taggle.bookmark.docs;

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
                        fieldWithPath("url").type(JsonFieldType.STRING).description("등록할 웹 페이지의 URL")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 북마크의 URI")
                ),
                responseFields(
                        fieldWithPath("id").description("북마크 ID"),
                        fieldWithPath("url").description("북마크 URL"),
                        fieldWithPath("title").description("북마크 타이틀"),
                        fieldWithPath("description").description("북마크 설명"),
                        fieldWithPath("image").description("북마크 이미지")
                )
        );
    }

    public static RestDocumentationResultHandler findBookmarks() {
        return document("bookmarks/get/List",
                requestParameters(
                        parameterWithName("offset").description("시작 페이지").optional(),
                        parameterWithName("limit").description("가져올 북마크의 수").optional()
                ),
                responseFields(
                        fieldWithPath("[].id").description("북마크 ID"),
                        fieldWithPath("[].url").description("북마크 URL"),
                        fieldWithPath("[].title").description("북마크 타이틀"),
                        fieldWithPath("[].description").description("북마크 설명"),
                        fieldWithPath("[].image").description("북마크 이미지")
                )
        );
    }

    public static RestDocumentationResultHandler removeBookmark() {
        return document("bookmarks/delete",
                pathParameters(
                        parameterWithName("bookmarkId").description("북마크 ID")
                )
        );
    }

}

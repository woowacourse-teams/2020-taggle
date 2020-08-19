package com.woowacourse.taggle.tag.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class TagBookmarkDocumentation {

    public static RestDocumentationResultHandler findBookmarksOfTag() {
        return document("tags/bookmarks/get",
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

    public static RestDocumentationResultHandler findBookmarksOfUntagged() {
        return document("tags/bookmarks/untagged/get",
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

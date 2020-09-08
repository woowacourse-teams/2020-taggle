package com.woowacourse.taggle.bookmark.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class TagBookmarkDocumentation {

    public static RestDocumentationResultHandler findBookmarksByTagId() {
        return document("tags/bookmarks/get",
                requestParameters(
                        parameterWithName("offset").description("시작 페이지").optional(),
                        parameterWithName("limit").description("가져올 북마크의 수").optional()
                ),
                pathParameters(
                        parameterWithName("tagId").description("태그 ID")
                ),
                responseFields(
                        fieldWithPath("id").description("태그 ID"),
                        fieldWithPath("name").description("태그 이름"),
                        fieldWithPath("bookmarks").description("북마크 (optional)").optional(),
                        fieldWithPath("bookmarks.[].id").description("북마크 ID"),
                        fieldWithPath("bookmarks.[].url").description("북마크 URI"),
                        fieldWithPath("bookmarks.[].title").description("북마크 타이틀"),
                        fieldWithPath("bookmarks.[].description").description("북마크 설명"),
                        fieldWithPath("bookmarks.[].image").description("북마크 이미지")
                ));
    }

    public static RestDocumentationResultHandler findUntaggedBookmarks() {
        return document("tags/bookmarks/untagged/get",
                requestParameters(
                        parameterWithName("offset").description("시작 페이지").optional(),
                        parameterWithName("limit").description("가져올 북마크의 수").optional()
                ),
                responseFields(
                        fieldWithPath("id").description("태그 ID (optional)").optional(),
                        fieldWithPath("name").description("태그 이름"),
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

    public static RestDocumentationResultHandler findTagsByBookmarkId() {
        return document("bookmarks/tags/get",
                pathParameters(
                        parameterWithName("bookmarkId").description("북마크 ID")
                ),
                responseFields(
                        fieldWithPath("id").description("북마크 ID"),
                        fieldWithPath("url").description("북마크 URL"),
                        fieldWithPath("tags").description("북마크가 가진 태그 목록").optional(),
                        fieldWithPath("tags.[].id").description("태그 ID"),
                        fieldWithPath("tags.[].name").description("태그 이름")
                )
        );
    }
}

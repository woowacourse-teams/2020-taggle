package kr.taggle.bookmark.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class BookmarkDocumentation {

    public static RestDocumentationResultHandler findBookmarks() {
        return document("bookmarks/get/List",
                requestParameters(
                        parameterWithName("offset").description("시작 페이지").optional(),
                        parameterWithName("limit").description("가져올 북마크의 수").optional()
                ),
                responseFields(
                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("북마크 ID"),
                        fieldWithPath("[].url").type(JsonFieldType.STRING).description("북마크 URL"),
                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("북마크 타이틀"),
                        fieldWithPath("[].description").type(JsonFieldType.STRING).description("북마크 설명"),
                        fieldWithPath("[].image").type(JsonFieldType.STRING).description("북마크 이미지")
                )
        );
    }

    public static RestDocumentationResultHandler findBookmarkDetail() {
        return document("bookmarks/get/detail",
                pathParameters(
                        parameterWithName("bookmarkId").description("북마크 ID")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("북마크 ID"),
                        fieldWithPath("url").type(JsonFieldType.STRING).description("북마크 URL"),
                        fieldWithPath("tags").type(JsonFieldType.ARRAY).description("태그 목록").optional(),
                        fieldWithPath("tags.[].id").type(JsonFieldType.NUMBER).description("태그 ID"),
                        fieldWithPath("tags.[].name").type(JsonFieldType.STRING).description("태그 이름")
                )
        );
    }

    public static RestDocumentationResultHandler findBookmarksByTagId() {
        return document("bookmarks/get/query-by-tag-id",
                requestParameters(
                        parameterWithName("tag").description("태그 ID"),
                        parameterWithName("offset").description("시작 페이지").optional(),
                        parameterWithName("limit").description("가져올 북마크의 수").optional()
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("태그 ID"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("태그 이름"),
                        fieldWithPath("bookmarks").type(JsonFieldType.ARRAY).description("북마크 목록").optional(),
                        fieldWithPath("bookmarks.[].id").type(JsonFieldType.NUMBER).description("북마크 ID"),
                        fieldWithPath("bookmarks.[].url").type(JsonFieldType.STRING).description("북마크 URI"),
                        fieldWithPath("bookmarks.[].title").type(JsonFieldType.STRING).description("북마크 타이틀"),
                        fieldWithPath("bookmarks.[].description").type(JsonFieldType.STRING).description("북마크 설명"),
                        fieldWithPath("bookmarks.[].image").type(JsonFieldType.STRING).description("북마크 이미지")
                ));
    }

    public static RestDocumentationResultHandler findUntaggedBookmarks() {
        return document("bookmarks/get/query-by-untagged-item",
                requestParameters(
                        parameterWithName("tag").description("태그 ID (none)"),
                        parameterWithName("offset").description("시작 페이지").optional(),
                        parameterWithName("limit").description("가져올 북마크의 수").optional()
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("태그 ID").optional(),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("태그 이름"),
                        fieldWithPath("bookmarks").type(JsonFieldType.ARRAY).description("북마크 목록").optional(),
                        fieldWithPath("bookmarks.[].id").type(JsonFieldType.NUMBER).description("북마크 ID"),
                        fieldWithPath("bookmarks.[].url").type(JsonFieldType.STRING).description("북마크 URL"),
                        fieldWithPath("bookmarks.[].title").type(JsonFieldType.STRING).description("북마크 타이틀"),
                        fieldWithPath("bookmarks.[].description").type(JsonFieldType.STRING).description("북마크 설명"),
                        fieldWithPath("bookmarks.[].image").type(JsonFieldType.STRING).description("북마크 이미지")
                ));
    }

    public static RestDocumentationResultHandler createBookmark() {
        return document("bookmarks/create",
                requestFields(
                        fieldWithPath("url").type(JsonFieldType.STRING).description("북마크 URL")
                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 북마크의 URI")
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("북마크 ID"),
                        fieldWithPath("url").type(JsonFieldType.STRING).description("북마크 URL"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("북마크 타이틀"),
                        fieldWithPath("description").type(JsonFieldType.STRING).description("북마크 설명"),
                        fieldWithPath("image").type(JsonFieldType.STRING).description("북마크 이미지")
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

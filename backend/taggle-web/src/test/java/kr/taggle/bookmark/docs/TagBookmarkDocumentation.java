package kr.taggle.bookmark.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class TagBookmarkDocumentation {

    public static RestDocumentationResultHandler findBookmarksByTagId() {
        return document("tags/bookmarks/get",
                requestParameters(
                        parameterWithName("tag").description("조회할 태그 id. 해당 태그를 포함하는 모든 북마크 목록을 조회한다."),
                        parameterWithName("offset").description("시작 페이지").optional(),
                        parameterWithName("limit").description("가져올 북마크의 수").optional()
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
                        parameterWithName("tag").description("조회할 태그 id. 태그가 없는 북마크 목록 조회를 위해서는 해당 parameter에 none이 들어가야 한다."),
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

    public static RestDocumentationResultHandler createTagBookmark() {
        return document("tags/bookmarks/create",
                pathParameters(
                        parameterWithName("bookmarkId").description("북마크 ID"),
                        parameterWithName("tagId").description("태그 ID")
                ),
                responseHeaders(
                        headerWithName("Location").description("태그 조회 URI")
                )
        );
    }

    public static RestDocumentationResultHandler removeTagBookmark() {
        return document("tags/bookmarks/delete",
                pathParameters(
                        parameterWithName("bookmarkId").description("북마크 ID"),
                        parameterWithName("tagId").description("태그 ID")
                )
        );
    }

    public static RestDocumentationResultHandler findBookmarkDetail() {
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

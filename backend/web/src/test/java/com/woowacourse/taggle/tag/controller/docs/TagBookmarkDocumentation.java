package com.woowacourse.taggle.tag.controller.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class TagBookmarkDocumentation {

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

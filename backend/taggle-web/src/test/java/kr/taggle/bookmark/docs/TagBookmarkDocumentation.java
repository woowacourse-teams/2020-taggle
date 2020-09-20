package kr.taggle.bookmark.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class TagBookmarkDocumentation {

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
}

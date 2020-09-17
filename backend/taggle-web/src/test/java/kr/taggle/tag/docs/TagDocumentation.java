package kr.taggle.tag.docs;

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
                ),
                responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("태그 ID"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("태그 이름")
                )
        );
    }

    public static RestDocumentationResultHandler updateTag() {
        return document("tags/update",
                pathParameters(
                        parameterWithName("tagId").description("태그 ID")
                ),
                requestFields(
                        fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID")
                )
        );
    }

    public static RestDocumentationResultHandler removeTag() {
        return document("tags/delete",
                pathParameters(
                        parameterWithName("tagId").description("태그 ID")
                )
        );
    }
}

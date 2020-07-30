package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.woowacourse.taggle.ControllerTest;
import com.woowacourse.taggle.setup.domain.BookmarkSetup;
import com.woowacourse.taggle.setup.domain.TagBookmarkSetup;
import com.woowacourse.taggle.setup.domain.TagSetup;
import com.woowacourse.taggle.tag.controller.docs.TagDocumentation;
import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Tag;

public class TagControllerTest extends ControllerTest {

    @Autowired
    private TagBookmarkSetup tagBookmarkSetup;

    @Autowired
    private TagSetup tagSetup;

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @WithMockUser(value = "ADMIN")
    @DisplayName("createTag: 태그를 추가한다.")
    @Test
    void createTag() throws Exception {
        createByJsonParams("/api/v1/tags", "{\"name\": \"taggle\"}")
                .andDo(TagDocumentation.createTag());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findTagById: 태그 단건을 조회한다.")
    @Test
    void findTagById() throws Exception {
        final Tag tag = tagSetup.save();
        final Bookmark bookmark = bookmarkSetup.save();
        tagBookmarkSetup.save(tag, bookmark);

        readByPathVariables("/api/v1/tags/{id}/bookmarks", tag.getId())
                .andExpect(jsonPath("$.id", is(tag.getId().intValue())))
                .andDo(TagDocumentation.findTag());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findTags: 전체 태그를 조회한다.")
    @Test
    void findTags() throws Exception {
        tagSetup.save();

        read("/api/v1/tags", jsonPath("$", hasSize(1)))
                .andDo(TagDocumentation.findTags());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("removeTag: 태그를 삭제한다.")
    @Test
    void removeTag() throws Exception {
        final Tag tag = tagSetup.save();

        remove("/api/v1/tags/{id}", tag.getId())
                .andDo(TagDocumentation.removeTags());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("addBookmarkOnTag: 태그에 북마크를 추가한다.")
    @Test
    void addBookmarkOnTag() throws Exception {
        final Tag tag = tagSetup.save();
        final Bookmark bookmark = bookmarkSetup.save();

        createByPathVariables("/api/v1/tags/{tagId}/bookmarks/{bookmarkId}", tag.getId(), bookmark.getId())
                .andDo(TagDocumentation.addBookmarkOnTag());
    }
}

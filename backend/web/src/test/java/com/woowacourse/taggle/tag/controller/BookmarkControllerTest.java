package com.woowacourse.taggle.tag.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.woowacourse.taggle.setup.domain.BookmarkSetup;
import com.woowacourse.taggle.tag.domain.Bookmark;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BookmarkControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BookmarkSetup bookmarkSetup;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("createBookmark: 북마크를 추가한다.")
    @Test
    void createBookmark() throws Exception {
        mockMvc.perform(post("/api/v1/bookmarks")
                .content("{ \"url\": \"http://github.com\" }")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() throws Exception {
        bookmarkSetup.save();

        mockMvc.perform(get("/api/v1/bookmarks")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @WithMockUser(value = "ADIMI")
    @DisplayName("removeBookmark: 북마크 하나를 제거한다.")
    @Test
    void removeBookmark() throws Exception {
        Bookmark bookmark = bookmarkSetup.save();

        mockMvc.perform(delete("/api/v1/bookmarks/" + bookmark.getId())
        )
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}

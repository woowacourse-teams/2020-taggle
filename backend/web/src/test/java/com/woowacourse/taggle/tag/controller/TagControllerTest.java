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

import com.woowacourse.taggle.setup.domain.TagSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class TagControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TagSetup tagSetup;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("createTag: 태그를 추가한다.")
    @Test
    void createTag() throws Exception {
        mockMvc.perform(post("/api/v1/tags")
                .content("{\"name\": \"taggle\"}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @WithMockUser(value = "ADMIN")
    @DisplayName("findBookmarks: 전체 북마크를 조회한다.")
    @Test
    void findBookmarks() throws Exception {
        tagSetup.save();

        mockMvc.perform(get("/api/v1/tags")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}

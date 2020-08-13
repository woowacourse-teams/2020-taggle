package com.woowacourse.taggle;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.woowacourse.taggle.authentication.UserArgumentResolver;
import com.woowacourse.taggle.tag.domain.Bookmark;
import com.woowacourse.taggle.tag.domain.Category;
import com.woowacourse.taggle.tag.domain.Tag;
import com.woowacourse.taggle.user.domain.UserRepository;
import com.woowacourse.taggle.user.dto.SessionUser;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Transactional
public class ControllerTest {

    @Autowired
    UserRepository userRepository;

    @MockBean
    private UserArgumentResolver userArgumentResolver;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext,
            final RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
        when(userArgumentResolver.supportsParameter(any())).thenReturn(true);

    }

    public ResultActions createByJsonParams(final String uri, final String jsonParams, final Category category) throws
            Exception {
        SessionUser sessionUser = new SessionUser(category.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(post(uri)
                .content(jsonParams)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    public ResultActions createByPathVariables(final String uri, final Tag tag) throws Exception {
        SessionUser sessionUser = new SessionUser(tag.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);

        return mockMvc.perform(post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    public ResultActions read(final String uri, final ResultMatcher expect, final Tag tag) throws Exception {
        SessionUser sessionUser = new SessionUser(tag.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(expect)
                .andDo(print());
    }

    public ResultActions readBookmark(final String uri, final ResultMatcher expect, final Bookmark bookmark) throws
            Exception {
        SessionUser sessionUser = new SessionUser(bookmark.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(expect)
                .andDo(print());
    }

    public ResultActions readByPathVariables(final String uri, final Tag tag) throws Exception {
        SessionUser sessionUser = new SessionUser(tag.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    public ResultActions readByBookmarkPathVariables(final String uri, final Bookmark bookmark) throws Exception {
        SessionUser sessionUser = new SessionUser(bookmark.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    public ResultActions remove(final String uri, final Category category) throws Exception {
        SessionUser sessionUser = new SessionUser(category.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(delete(uri)
        )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    public ResultActions removeBookmark(final String uri, final Bookmark bookmark) throws Exception {
        SessionUser sessionUser = new SessionUser(bookmark.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(delete(uri)
        )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    public ResultActions removeTagAndBookmark(final String uri, final Bookmark bookmark) throws Exception {
        SessionUser sessionUser = new SessionUser(bookmark.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(delete(uri)
        )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    public ResultActions updateByPathVariables(final String uri, final Object... ids) throws Exception {
        return mockMvc.perform(put(uri, ids)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    public ResultActions updateByJsonParams(final String uri, final String jsonParams, final Category category) throws
            Exception {
        SessionUser sessionUser = new SessionUser(category.getUser());
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(put(uri)
                .content(jsonParams)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}


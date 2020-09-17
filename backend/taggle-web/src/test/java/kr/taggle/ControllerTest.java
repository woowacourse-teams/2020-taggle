package kr.taggle;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import kr.taggle.authentication.UserArgumentResolver;
import kr.taggle.user.domain.User;
import kr.taggle.user.dto.SessionUser;

@ExtendWith(RestDocumentationExtension.class)
@WithMockUser(roles = "USER")
@SpringBootTest
@Transactional
public class ControllerTest {

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

    public ResultActions createByJsonParams(final User user, final String uri, final String jsonParams) throws
            Exception {
        final SessionUser sessionUser = new SessionUser(user);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(post(uri)
                .content(jsonParams)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    public ResultActions createByPathVariables(final User user, final String uri, final Object... ids) throws
            Exception {
        final SessionUser sessionUser = new SessionUser(user);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(post(uri, ids)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    public ResultActions read(final User user, final String uri, final ResultMatcher expect) throws
            Exception {
        final SessionUser sessionUser = new SessionUser(user);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(expect)
                .andDo(print());
    }

    public ResultActions readByPathVariables(final User user, final String uri, final Long id) throws Exception {
        final SessionUser sessionUser = new SessionUser(user);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(get(uri, id)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    public ResultActions remove(final User user, final String uri) throws
            Exception {
        final SessionUser sessionUser = new SessionUser(user);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(delete(uri))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    public ResultActions removeByPathVariables(final User user, final String uri, final Object... ids) throws
            Exception {
        final SessionUser sessionUser = new SessionUser(user);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(delete(uri, ids))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    public ResultActions updateByJsonParamsAndPathVariables(final User user, final String uri, final String jsonParams, final Object... ids) throws
            Exception {
        final SessionUser sessionUser = new SessionUser(user);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(put(uri, ids)
                .content(jsonParams)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    public ResultActions updateByJsonParams(final User user, final String uri, final String jsonParams) throws
            Exception {
        final SessionUser sessionUser = new SessionUser(user);
        when(userArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(sessionUser);
        return mockMvc.perform(put(uri)
                .content(jsonParams)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }
}

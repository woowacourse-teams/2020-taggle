package kr.taggle.common.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootTest
public class WebMvcConfigTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new ShallowEtagHeaderFilter())
                .build();
    }

    @DisplayName("정적 리소스 요청에 Etag가 설정 되고 no-cache, must-revalidate로 Cache-Control 설정된다.")
    @Test
    void getEtagAndCacheControl() throws Exception {
        //given
        String uri = "/taggle-favicon.ico";

        //then
        mockMvc.perform(get(uri).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Etag"))
                .andExpect(header().string("Cache-Control","no-cache, must-revalidate, public"));
    }

    @DisplayName("Etag가 변경되지 않으면 304 status를 반환한다")
    @Test
    void UnChangedEtag() throws Exception {
        //given
        String uri = "/img/google-button.57a6f216.png";

        //when
        MvcResult mvcResult = mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("Etag"))
                .andReturn();

        String etag = mvcResult.getResponse().getHeader("Etag");

        //then
        mockMvc.perform(get(uri).header("If-None-Match", etag))
                .andDo(print())
                .andExpect(status().isNotModified())
                .andExpect(header().exists("Etag"))
                .andReturn();
    }

    @DisplayName("Etag가 다를경우 200 status를 반환한다.")
    @Test
    void differentEtag() throws Exception {
        //given
        String uri = "/img/google-button.57a6f216.png";

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists("ETag"))
                .andExpect(header().exists("Cache-Control"))
                .andReturn();

        //then
        mockMvc.perform(get(uri).header("If-None-Match","anotherEtag"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
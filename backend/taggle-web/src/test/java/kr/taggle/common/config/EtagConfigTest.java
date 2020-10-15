package kr.taggle.common.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class EtagConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("getEtagAndCacheControl: 정적 리소스 요청에 Etag가 설정 되고 no-cache, must-revalidate로 Cache-Control 설정된다.")
    @Test
    void getEtagAndCacheControl() throws Exception {
        //given
        String uri = "/taggle-favicon.ico";

        //when
        //then
        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.ETAG))
                .andExpect(header().string(HttpHeaders.CACHE_CONTROL,"no-cache, must-revalidate, public"));
    }

    @DisplayName("UnChangedEtag: Etag가 변경되지 않으면 304 status를 반환한다")
    @Test
    void UnChangedEtag() throws Exception {
        //given
        String uri = "/img/google-button.57a6f216.png";

        //when
        MvcResult mvcResult = mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.ETAG))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                .andReturn();

        String etag = mvcResult.getResponse().getHeader(HttpHeaders.ETAG);

        //then
        mockMvc.perform(get(uri).header(HttpHeaders.IF_NONE_MATCH, etag))
                .andDo(print())
                .andExpect(status().isNotModified())
                .andExpect(header().exists(HttpHeaders.ETAG));
    }

    @DisplayName("differentEtag: Etag가 다를경우 200 status를 반환한다.")
    @Test
    void differentEtag() throws Exception {
        //given
        String uri = "/img/google-button.57a6f216.png";

        //when
        //then
        mockMvc.perform(get(uri).header(HttpHeaders.IF_NONE_MATCH,"anotherEtag"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.ETAG))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
    }
}
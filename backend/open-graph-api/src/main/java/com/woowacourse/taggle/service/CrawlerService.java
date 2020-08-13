package com.woowacourse.taggle.service;

import java.io.IOException;

import org.hibernate.validator.constraints.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.woowacourse.taggle.dto.OpenGraphDto;
import com.woowacourse.taggle.exception.NotConnectCrawlerException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CrawlerService {
    private final static String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";

    public OpenGraphDto openGraphCrawler(@URL(message = "올바른 url을 입력해주세요.") String url) {
        try {
            Document document = Jsoup
                    .connect(url)
                    .userAgent(USER_AGENT)
                    .get();
            String title = document.select("meta[property=og:title]").attr("content");
            if (title.equals("")) {
                title = document.title();
            }
            String description = document.select("meta[property=og:description]").attr("content");
            String image = document.select("meta[property=og:image]").attr("content");
            return new OpenGraphDto(title, description, image);
        } catch (IOException e) {
            throw new NotConnectCrawlerException("연결이 되지 않았습니다.");
        }
    }
}

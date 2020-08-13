package com.woowacourse.taggle.crawler;

import java.io.IOException;

import org.hibernate.validator.constraints.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.woowacourse.taggle.dto.OpenGraphDto;
import com.woowacourse.taggle.exception.NotConnectCrawlerException;

public class OpenGraphCrawler {
    private final static String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
    private final static String META_TITLE = "meta[property=og:title]";
    private final static String META_DESCRIPTION = "meta[property=og:description]";
    private final static String META_IMAGE = "meta[property=og:image]";

    private Document jsoupConfig(String url) throws IOException {
        return Jsoup
                .connect(url)
                .userAgent(USER_AGENT)
                .get();
    }

    public OpenGraphDto findOpenGraph(@URL(message = "올바른 url을 입력해주세요.") String url) {
        try {
            Document document = jsoupConfig(url);
            String title = document.select(META_TITLE).attr("content");
            if (title.equals("")) {
                title = document.title();
            }
            String description = document.select(META_DESCRIPTION).attr("content");
            String image = document.select(META_IMAGE).attr("content");
            return new OpenGraphDto(title, description, image);
        } catch (IOException e) {
            throw new NotConnectCrawlerException("연결이 되지 않았습니다.");
        }
    }
}

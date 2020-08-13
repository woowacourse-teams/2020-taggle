package com.woowacourse.taggle.crawler;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.woowacourse.taggle.dto.OpenGraphDto;
import com.woowacourse.taggle.exception.CrawlerConnectionException;
import com.woowacourse.taggle.exception.InvalidURLException;

public class OpenGraphCrawler {
    private final static String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
    private final static String META_TITLE = "meta[property=og:title]";
    private final static String META_DESCRIPTION = "meta[property=og:description]";
    private final static String META_IMAGE = "meta[property=og:image]";
    private final static String URL_REGEX = "^(https?)://?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private final static Pattern pattern = Pattern.compile(URL_REGEX);

    private Document jsoupConfig(String url) throws IOException {
        return Jsoup
                .connect(url)
                .userAgent(USER_AGENT)
                .get();
    }

    public OpenGraphDto findOpenGraph(String url) {
        validateURL(url);
        try {
            Document document = jsoupConfig(url);
            String title = document.select(META_TITLE).attr("content");
            if (title.isEmpty()) {
                title = document.title();
            }
            String description = document.select(META_DESCRIPTION).attr("content");
            String image = document.select(META_IMAGE).attr("content");
            return new OpenGraphDto(title, description, image);
        } catch (IOException e) {
            throw new CrawlerConnectionException("연결이 되지 않았습니다.");
        }
    }

    private void validateURL(String url) {
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
            throw new InvalidURLException("잘못된 URL입니다. url : " + url);
        }
    }
}

package com.woowacourse.taggle.linkpreview;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.woowacourse.taggle.exception.CrawlerConnectionException;
import com.woowacourse.taggle.exception.InvalidURLException;

public class LinkPreviewCrawler {
    private final static String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
    private final static String META_TITLE = "meta[property=og:title]";
    private final static String META_DESCRIPTION = "meta[property=og:description]";
    private final static String META_IMAGE = "meta[property=og:image]";
    private final static String URL_REGEX = "^(https?)://?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private final static Pattern pattern = Pattern.compile(URL_REGEX);
    private final static String CONTENT = "content";

    private final String defaultImage;

    public LinkPreviewCrawler(final String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public LinkPreview extractPreview(final String url) {
        validateURL(url);
        try {
            final Document document = jsoupConfig(url);
            final String title = getTitle(document, url);
            final String description = getDescription(document);
            final String image = getImage(document);
            return new LinkPreview(title, description, image);
        } catch (final IOException e) {
            throw new CrawlerConnectionException("연결이 되지 않았습니다. url : " + url);
        }
    }

    private Document jsoupConfig(final String url) throws IOException {
        return Jsoup
                .connect(url)
                .userAgent(USER_AGENT)
                .followRedirects(true)
                .get();
    }

    private String getImage(final Document document) {
        final String image = document.select(META_IMAGE).attr(CONTENT);
        if (image.isEmpty()) {
            return defaultImage;
        }
        return image;
    }

    private String getDescription(final Document document) {
        return document.select(META_DESCRIPTION).attr(CONTENT);
    }

    private String getTitle(final Document document, final String url) {
        String title = document.select(META_TITLE).attr(CONTENT);
        if (title.isEmpty()) {
            title = document.title();
        }
        if (title.isEmpty()) {
            return url;
        }
        return title;
    }

    private void validateURL(final String url) {
        final Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
            throw new InvalidURLException("잘못된 URL입니다. url : " + url);
        }
    }
}

package com.woowacourse.taggle.linkpreview;

public class LinkPreview {

    private final String title;
    private final String description;
    private final String image;

    public LinkPreview(final String title, final String description, final String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}

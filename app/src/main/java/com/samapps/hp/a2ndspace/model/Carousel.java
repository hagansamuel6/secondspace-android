package com.samapps.hp.a2ndspace.model;

public class Carousel {

    String caption;
    String url;

    public Carousel() {
    }

    public Carousel(String caption, String url) {
        this.caption = caption;
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

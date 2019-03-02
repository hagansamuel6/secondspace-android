package com.samapps.hp.a2ndspace.model;

public class Photo {
    String url;
    String caption;

    public Photo() {
    }

    public Photo(String url, String caption) {
        this.url = url;
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}

package com.samapps.hp.a2ndspace.model;

import java.util.List;

public class AdThumbnail {
    List<Carousel> mCarousels;

    public AdThumbnail() {
    }

    public AdThumbnail(List<Carousel> mCarousels) {
        this.mCarousels = mCarousels;
    }

    public List<Carousel> getmCarousels() {
        return mCarousels;
    }

    public void setmCarousels(List<Carousel> mCarousels) {
        this.mCarousels = mCarousels;
    }
}

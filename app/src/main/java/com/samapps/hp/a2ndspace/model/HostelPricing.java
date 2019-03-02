package com.samapps.hp.a2ndspace.model;

public class HostelPricing {
    private String hostelRoomPrice;
    private String hostelRoomType;


    public HostelPricing() {
    }

    public HostelPricing(String hostelRoomPrice, String hostelRoomType) {
        this.hostelRoomPrice = hostelRoomPrice;
        this.hostelRoomType = hostelRoomType;
    }

    public String getHostelRoomPrice() {
        return hostelRoomPrice;
    }

    public void setHostelRoomPrice(String hostelRoomPrice) {
        this.hostelRoomPrice = hostelRoomPrice;
    }

    public String getHostelRoomType() {
        return hostelRoomType;
    }

    public void setHostelRoomType(String hostelRoomType) {
        this.hostelRoomType = hostelRoomType;
    }
}

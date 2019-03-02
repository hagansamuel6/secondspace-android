package com.samapps.hp.a2ndspace.model;

import java.util.List;

public class Hostel {

    private String mHostelName;
    private String mHostelLocation;
    private String mHostelImage;
    private String mHostelAreaId;
    private String mHostelDescription;
    private String mHostelAvailability;
    private String mHostelContact;
    private List<Photo> mHostelPhotos;
    private List<Amenities> mHostelAmenities;
    private List<HostelPricing> mHostelPricing;


    public Hostel() {
    }

    public Hostel(String mHostelName, String mHostelLocation, String mHostelImage, String mHostelAreaId, List<Photo> HostelPhotos) {
        this.mHostelName = mHostelName;
        this.mHostelLocation = mHostelLocation;
        this.mHostelImage = mHostelImage;
        this.mHostelAreaId = mHostelAreaId;
        this.mHostelPhotos = HostelPhotos;
    }

    public List<HostelPricing> getmHostelPricing() {
        return mHostelPricing;
    }

    public void setmHostelPricing(List<HostelPricing> mHostelPricing) {
        this.mHostelPricing = mHostelPricing;
    }

    public String getmHostelDescription() {
        return mHostelDescription;
    }

    public String getmHostelAvailability() {
        return mHostelAvailability;
    }

    public void setmHostelSpaceAvailability(String mHostelSpaceAvailability) {
        this.mHostelAvailability = mHostelSpaceAvailability;
    }

    public void setmHostelDescription(String mHostelDescription) {
        this.mHostelDescription = mHostelDescription;
    }

    public String getmHostelContact() {
        return mHostelContact;
    }

    public void setmHostelContact(String mHostelContact) {
        this.mHostelContact = mHostelContact;
    }

    public String getmHostelAreaId() {
        return mHostelAreaId;
    }

    public void setmHostelAreaId(String mHostelAreaId) {
        this.mHostelAreaId = mHostelAreaId;
    }

    public String getmHostelImage() {
        return mHostelImage;
    }

    public void setmHostelImage(String mHostelImage) {
        this.mHostelImage = mHostelImage;
    }

    public String getmHostelName() {
        return mHostelName;
    }

    public void setmHostelName(String mHostelName) {
        this.mHostelName = mHostelName;
    }

    public String getmHostelLocation() {
        return mHostelLocation;
    }

    public void setmHostelLocation(String mHostelLocation) {
        this.mHostelLocation = mHostelLocation;
    }

    public void setmHostelAvailability(String mHostelAvailability) {
        this.mHostelAvailability = mHostelAvailability;
    }

    public List<Amenities> getmHostelAmenities() {
        return mHostelAmenities;
    }

    public void setmHostelAmenities(List<Amenities> mHostelAmenities) {
        this.mHostelAmenities = mHostelAmenities;
    }

    public List<Photo> getmHostelPhotos() {
        return mHostelPhotos;
    }

    public void setmHostelPhotos(List<Photo> mHostelPhotos) {
        this.mHostelPhotos = mHostelPhotos;
    }
}

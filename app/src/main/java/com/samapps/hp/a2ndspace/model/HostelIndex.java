package com.samapps.hp.a2ndspace.model;

public class HostelIndex {
    private String HostelAreaId;
    private String HostelName;

    public HostelIndex() {
    }

    public HostelIndex(String hostelAreaId, String hostelName) {
        HostelAreaId = hostelAreaId;
        HostelName = hostelName;
    }

    public String getHostelAreaId() {
        return HostelAreaId;
    }

    public void setHostelAreaId(String hostelAreaId) {
        HostelAreaId = hostelAreaId;
    }

    public String getHostelName() {
        return HostelName;
    }

    public void setHostelName(String hostelName) {
        HostelName = hostelName;
    }
}

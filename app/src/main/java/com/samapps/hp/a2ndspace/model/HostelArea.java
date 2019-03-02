package com.samapps.hp.a2ndspace.model;

import java.util.List;

public class HostelArea {
    private String mHostelAreaId;
    private String mName;
    private List<Hostel> mHostelsForArea;


    public HostelArea() {
    }

    public String getmHostelAreaId() {
        return mHostelAreaId;
    }

    public void setmHostelAreaId(String mHostelAreaId) {
        this.mHostelAreaId = mHostelAreaId;
    }

    public HostelArea(String mHostelAreaId, String mName, List<Hostel> mHostelsForArea) {
        this.mHostelAreaId = mHostelAreaId;
        this.mName = mName;
        this.mHostelsForArea = mHostelsForArea;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public List<Hostel> getmHostelsForArea() {
        return mHostelsForArea;
    }

    public void setmHostelsForArea(List<Hostel> mHostelsForArea) {
        this.mHostelsForArea = mHostelsForArea;
    }
}

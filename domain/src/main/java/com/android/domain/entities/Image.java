package com.android.domain.entities;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("#text")
    private String url;
    private String size;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
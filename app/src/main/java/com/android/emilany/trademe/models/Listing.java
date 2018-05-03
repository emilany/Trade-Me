package com.android.emilany.trademe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Listing extends ListingItem {
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("Photos")
    @Expose
    private List<Photo> photoList;
    @SerializedName("PaymentOptions")
    @Expose
    private String paymentOptions;

    public Listing() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public String getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(String paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

}

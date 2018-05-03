package com.android.emilany.trademe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeneralListing {
    @SerializedName("List")
    @Expose
    private List<ListingItem> listingItems;

    public GeneralListing() {
    }

    public List<ListingItem> getListingItems() {
        return listingItems;
    }

    public void setListingItems(List<ListingItem> listingItems) {
        this.listingItems = listingItems;
    }
}

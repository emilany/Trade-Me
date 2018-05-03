package com.android.emilany.trademe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Number")
    @Expose
    private String number;
    @SerializedName("Path")
    @Expose
    private String path;
    @SerializedName("Subcategories")
    @Expose
    private List<Category> subcategories;
    @SerializedName("IsLeaf")
    @Expose
    private boolean isLeaf;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories = subcategories;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
}
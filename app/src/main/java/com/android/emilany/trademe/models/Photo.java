package com.android.emilany.trademe.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("Key")
    @Expose
    private String body;
    @SerializedName("Value")
    @Expose
    private PhotoItem value;

    public Photo() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public PhotoItem getValue() {
        return value;
    }

    public void setValue(PhotoItem value) {
        this.value = value;
    }

    public static class PhotoItem {
        @SerializedName("Thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("Medium")
        @Expose
        private String medium;
        @SerializedName("PhotoId")
        @Expose
        private Integer photoId;

        public PhotoItem() {
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public Integer getPhotoId() {
            return photoId;
        }

        public void setPhotoId(Integer photoId) {
            this.photoId = photoId;
        }
    }
}

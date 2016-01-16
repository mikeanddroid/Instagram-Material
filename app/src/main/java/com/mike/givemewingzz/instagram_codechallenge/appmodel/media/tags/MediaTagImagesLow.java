package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaTagImagesLow extends RealmObject {

    @SerializedName("url")
    private String imageURL;

    private int width;

    private int height;

    public MediaTagImagesLow() {
    }

    public MediaTagImagesLow(MediaTagImagesLow mediaTagImagesLow) {
        this.imageURL = mediaTagImagesLow.getImageURL();
        this.width = mediaTagImagesLow.getWidth();
        this.height = mediaTagImagesLow.getHeight();
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaImages extends RealmObject {

    @SerializedName("low_resolution")
    private MediaImagesLow mediaImagesLow;

    @SerializedName("standard_resolution")
    private MediaImagesStandard mediaImagesStandard;

    @SerializedName("thumbnail")
    private MediaImagesThumb mediaImagesThumb;

    public MediaImages() {
    }

    public MediaImages(MediaImages mediaImages) {
        this.mediaImagesLow = mediaImages.getMediaImagesLow();
        this.mediaImagesThumb = mediaImages.getMediaImagesThumb();
        this.mediaImagesStandard = mediaImages.getMediaImagesStandard();
    }

    public MediaImagesLow getMediaImagesLow() {
        return mediaImagesLow;
    }

    public void setMediaImagesLow(MediaImagesLow mediaImagesLow) {
        this.mediaImagesLow = mediaImagesLow;
    }

    public MediaImagesStandard getMediaImagesStandard() {
        return mediaImagesStandard;
    }

    public void setMediaImagesStandard(MediaImagesStandard mediaImagesStandard) {
        this.mediaImagesStandard = mediaImagesStandard;
    }

    public MediaImagesThumb getMediaImagesThumb() {
        return mediaImagesThumb;
    }

    public void setMediaImagesThumb(MediaImagesThumb mediaImagesThumb) {
        this.mediaImagesThumb = mediaImagesThumb;
    }
}

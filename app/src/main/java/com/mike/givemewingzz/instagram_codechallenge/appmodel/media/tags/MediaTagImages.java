package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaTagImages extends RealmObject {

    @SerializedName("low_resolution")
    private MediaTagImagesLow mediaTagImagesLow;

    @SerializedName("standard_resolution")
    private MediaTagImagesStandard mediaTagImagesStandard;

    @SerializedName("thumbnail")
    private MediaTagImagesThumb mediaTagImagesThumb;

    public MediaTagImages() {
    }

    public MediaTagImages(MediaTagImages mediaTagImages) {
        this.mediaTagImagesLow = mediaTagImages.getMediaTagImagesLow();
        this.mediaTagImagesThumb = mediaTagImages.getMediaTagImagesThumb();
        this.mediaTagImagesStandard = mediaTagImages.getMediaTagImagesStandard();
    }

    public MediaTagImagesLow getMediaTagImagesLow() {
        return mediaTagImagesLow;
    }

    public void setMediaTagImagesLow(MediaTagImagesLow mediaTagImagesLow) {
        this.mediaTagImagesLow = mediaTagImagesLow;
    }

    public MediaTagImagesStandard getMediaTagImagesStandard() {
        return mediaTagImagesStandard;
    }

    public void setMediaTagImagesStandard(MediaTagImagesStandard mediaTagImagesStandard) {
        this.mediaTagImagesStandard = mediaTagImagesStandard;
    }

    public MediaTagImagesThumb getMediaTagImagesThumb() {
        return mediaTagImagesThumb;
    }

    public void setMediaTagImagesThumb(MediaTagImagesThumb mediaTagImagesThumb) {
        this.mediaTagImagesThumb = mediaTagImagesThumb;
    }
}

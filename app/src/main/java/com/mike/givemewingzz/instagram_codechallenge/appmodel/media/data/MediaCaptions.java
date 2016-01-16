package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GiveMeWingzz on 1/13/2016.
 */
public class MediaCaptions extends RealmObject {

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("text")
    private String captionText;

    @SerializedName("from")
    private MediaCaptionFrom mediaCaptionFrom;

    @PrimaryKey
    private String id;

    public MediaCaptions() {
    }

    public MediaCaptions(MediaCaptions mediaCaptions) {
        this.createdTime = mediaCaptions.getCreatedTime();
        this.captionText = mediaCaptions.getCaptionText();
        this.mediaCaptionFrom = mediaCaptions.getMediaCaptionFrom();
        this.id = mediaCaptions.getId();
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCaptionText() {
        return captionText;
    }

    public void setCaptionText(String captionText) {
        this.captionText = captionText;
    }

    public MediaCaptionFrom getMediaCaptionFrom() {
        return mediaCaptionFrom;
    }

    public void setMediaCaptionFrom(MediaCaptionFrom mediaCaptionFrom) {
        this.mediaCaptionFrom = mediaCaptionFrom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

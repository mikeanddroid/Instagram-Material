package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GiveMeWingzz on 1/13/2016.
 */
public class MediaTagCaptions extends RealmObject {

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("text")
    private String captionText;

    @SerializedName("from")
    private MediaTagCaptionFrom mediaTagCaptionFrom;

    @PrimaryKey
    private String id;

    public MediaTagCaptions() {
    }

    public MediaTagCaptions(MediaTagCaptions mediaTagCaptions) {
        this.createdTime = mediaTagCaptions.getCreatedTime();
        this.captionText = mediaTagCaptions.getCaptionText();
        this.mediaTagCaptionFrom = mediaTagCaptions.getMediaTagCaptionFrom();
        this.id = mediaTagCaptions.getId();
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

    public MediaTagCaptionFrom getMediaTagCaptionFrom() {
        return mediaTagCaptionFrom;
    }

    public void setMediaTagCaptionFrom(MediaTagCaptionFrom mediaTagCaptionFrom) {
        this.mediaTagCaptionFrom = mediaTagCaptionFrom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

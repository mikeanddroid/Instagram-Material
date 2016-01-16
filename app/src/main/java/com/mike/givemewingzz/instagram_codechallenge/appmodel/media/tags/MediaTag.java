package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/13/2016.
 */
public class MediaTag extends RealmObject {

    @SerializedName("link")
    private String instaLink;

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("images")
    private MediaTagImages mediaImages;

    @SerializedName("caption")
    private MediaTagCaptions mediaCaptions;

    @SerializedName("likes")
    private MediaTagLikes mediaTagLikes;

    private String type;

    private String filter;

    @SerializedName("id")
    private String mediaID;

    public String getInstaLink() {
        return instaLink;
    }

    public void setInstaLink(String instaLink) {
        this.instaLink = instaLink;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    public MediaTagImages getMediaImages() {
        return mediaImages;
    }

    public void setMediaImages(MediaTagImages mediaImages) {
        this.mediaImages = mediaImages;
    }

    public MediaTagCaptions getMediaCaptions() {
        return mediaCaptions;
    }

    public void setMediaCaptions(MediaTagCaptions mediaCaptions) {
        this.mediaCaptions = mediaCaptions;
    }

    public MediaTagLikes getMediaTagLikes() {
        return mediaTagLikes;
    }

    public void setMediaTagLikes(MediaTagLikes mediaTagLikes) {
        this.mediaTagLikes = mediaTagLikes;
    }
}

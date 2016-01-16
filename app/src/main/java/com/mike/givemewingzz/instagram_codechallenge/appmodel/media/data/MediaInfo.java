package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaInfo extends RealmObject {

    @SerializedName("link")
    private String instaLink;

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("images")
    private MediaImages mediaImages;

    @SerializedName("caption")
    private MediaCaptions mediaCaptions;

    private String type;

    private String filter;

    @SerializedName("location")
    private MediaLocation mediaLocation;

    @SerializedName("id")
    private String mediaID;

    @SerializedName("likes")
    private MediaLikes mediaLikes;

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

    public MediaImages getMediaImages() {
        return mediaImages;
    }

    public void setMediaImages(MediaImages mediaImages) {
        this.mediaImages = mediaImages;
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

    public MediaLocation getMediaLocation() {
        return mediaLocation;
    }

    public void setMediaLocation(MediaLocation mediaLocation) {
        this.mediaLocation = mediaLocation;
    }

    public MediaCaptions getMediaCaptions() {
        return mediaCaptions;
    }

    public void setMediaCaptions(MediaCaptions mediaCaptions) {
        this.mediaCaptions = mediaCaptions;
    }

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    public MediaLikes getMediaLikes() {
        return mediaLikes;
    }

    public void setMediaLikes(MediaLikes mediaLikes) {
        this.mediaLikes = mediaLikes;
    }
}

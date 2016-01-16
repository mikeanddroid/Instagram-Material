package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/13/2016.
 */
public class MediaTagCaptionFrom extends RealmObject {

    @SerializedName("username")
    private String userName;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("type")
    private String type;

    public MediaTagCaptionFrom() {
    }

    public MediaTagCaptionFrom(MediaTagCaptionFrom mediaTagCaptionFrom) {
        this.userName = mediaTagCaptionFrom.getUserName();
        this.fullName = mediaTagCaptionFrom.getFullName();
        this.type = mediaTagCaptionFrom.getType();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

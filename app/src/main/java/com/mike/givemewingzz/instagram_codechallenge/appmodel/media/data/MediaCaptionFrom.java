package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/13/2016.
 */
public class MediaCaptionFrom extends RealmObject {

    @SerializedName("username")
    private String userName;

    @SerializedName("id")
    private String id;

    public MediaCaptionFrom() {
    }

    public MediaCaptionFrom(MediaCaptionFrom mediaCaptionFrom) {
        this.userName = mediaCaptionFrom.getUserName();
        this.id = mediaCaptionFrom.getId();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

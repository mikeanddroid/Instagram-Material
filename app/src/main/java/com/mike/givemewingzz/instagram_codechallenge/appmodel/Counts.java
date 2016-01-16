package com.mike.givemewingzz.instagram_codechallenge.appmodel;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/11/2016.
 */
public class Counts extends RealmObject {

    @SerializedName("media")
    private String userMediaCount;

    @SerializedName("follows")
    private String userFollows;

    @SerializedName("followed_by")
    private String userFollowedBy;

    public Counts() {
    }

    public Counts(Counts copy) {
        this.userMediaCount = copy.getUserMediaCount();
        this.userFollows = copy.getUserFollows();
        this.userFollowedBy = copy.getUserFollowedBy();
    }

    public String getUserMediaCount() {
        return userMediaCount;
    }

    public void setUserMediaCount(String userMediaCount) {
        this.userMediaCount = userMediaCount;
    }

    public String getUserFollows() {
        return userFollows;
    }

    public void setUserFollows(String userFollows) {
        this.userFollows = userFollows;
    }

    public String getUserFollowedBy() {
        return userFollowedBy;
    }

    public void setUserFollowedBy(String userFollowedBy) {
        this.userFollowedBy = userFollowedBy;
    }
}

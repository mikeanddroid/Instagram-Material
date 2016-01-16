package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GiveMeWingzz on 1/14/2016.
 */
public class MediaInfoUsers extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    @Ignore
    private String userId;

    @SerializedName("username")
    private String userName;

    @SerializedName("full_name")
    private String userFullName;

    @SerializedName("profile_picture")
    private String userProfilePicture;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

}

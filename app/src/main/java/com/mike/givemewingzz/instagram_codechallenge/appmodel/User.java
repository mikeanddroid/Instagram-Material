package com.mike.givemewingzz.instagram_codechallenge.appmodel;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GiveMeWingzz on 1/11/2016.
 */
public class User extends RealmObject {

    @SerializedName("id")
    @PrimaryKey
    private String userId;

    @SerializedName("username")
    private String userName;

    @SerializedName("full_name")
    private String userFullName;

    @SerializedName("profile_picture")
    private String userProfilePicture;

    @SerializedName("bio")
    private String userBio;

    @SerializedName("website")
    private String userWebsite;

    @SerializedName("counts")
    private Counts userTypeCounts;

    public User() {
    }

    public User(User copy) {
        this.userId = copy.getUserId();
        this.userName = copy.getUserName();
        this.userFullName = copy.getUserFullName();
        this.userProfilePicture = copy.getUserProfilePicture();
        this.userBio = copy.getUserBio();
        this.userWebsite = copy.getUserWebsite();
        this.userTypeCounts = copy.getUserTypeCounts();
    }

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

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserWebsite() {
        return userWebsite;
    }

    public void setUserWebsite(String userWebsite) {
        this.userWebsite = userWebsite;
    }

    public Counts getUserTypeCounts() {
        return userTypeCounts;
    }

    public void setUserTypeCounts(Counts userTypeCounts) {
        this.userTypeCounts = userTypeCounts;
    }
}

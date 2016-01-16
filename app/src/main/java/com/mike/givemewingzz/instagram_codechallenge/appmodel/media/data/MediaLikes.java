package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/13/2016.
 */
public class MediaLikes extends RealmObject {

    @SerializedName("count")
    private int likesCount;

    @SerializedName("data")
    private RealmList<MediaInfoUsers> mediaTagUsers;

    public MediaLikes() {
    }

    public MediaLikes(MediaLikes mediaLikes) {
        this.likesCount = mediaLikes.getLikesCount();
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public RealmList<MediaInfoUsers> getMediaTagUsers() {
        return mediaTagUsers;
    }

    public void setMediaTagUsers(RealmList<MediaInfoUsers> mediaTagUsers) {
        this.mediaTagUsers = mediaTagUsers;
    }
}

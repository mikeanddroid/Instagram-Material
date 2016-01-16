package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/13/2016.
 */
public class MediaTagLikes extends RealmObject {

    @SerializedName("count")
    private int likesCount;

    @SerializedName("data")
    private RealmList<MediaTagUsers> mediaTagUsers;

    public MediaTagLikes() {
    }

    public MediaTagLikes(MediaTagLikes mediaTagLikes) {
        this.likesCount = mediaTagLikes.getLikesCount();
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public RealmList<MediaTagUsers> getMediaTagUsers() {
        return mediaTagUsers;
    }

    public void setMediaTagUsers(RealmList<MediaTagUsers> mediaTagUsers) {
        this.mediaTagUsers = mediaTagUsers;
    }
}

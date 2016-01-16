package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaInfoWrapper extends RealmObject {

    private RealmList<MediaInfo> data;

    public void setData(RealmList<MediaInfo> data) {
        this.data = data;
    }

    public RealmList<MediaInfo> getData() {
        return data;
    }

}

package com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data;

import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaLocation extends RealmObject {

    private double latitude;

    private double longitude;

    private String id;

    public MediaLocation() {
    }

    public MediaLocation(MediaLocation mediaLocation) {
        this.latitude = mediaLocation.getLatitude();
        this.longitude = mediaLocation.getLongitude();
        this.id = mediaLocation.getId();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

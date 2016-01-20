package com.mike.givemewingzz.instagram_codechallenge.appmodel;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/19/2016.
 */
public class ListWrapper<T extends RealmObject> {

    private RealmList<T> data;

    public RealmList<T> getData() {
        return data;
    }

}

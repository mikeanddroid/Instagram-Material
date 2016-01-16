package com.mike.givemewingzz.instagram_codechallenge.appmodel.media;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by GiveMeWingzz on 1/14/2016.
 */
public class UsersListWrapper extends RealmObject {

    private RealmList<UsersList> data;

    public RealmList<UsersList> getData() {
        return data;
    }

    public void setData(RealmList<UsersList> data) {
        this.data = data;
    }
}

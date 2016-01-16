package com.mike.givemewingzz.instagram_codechallenge.utils;

import android.os.Bundle;

/**
 * Created by GiveMeWingzz on 1/7/2016.
 */
public class QueryParamsBundle {

    public static Bundle bundle;

    public QueryParamsBundle() {
        bundle = new Bundle();
    }

    public void addQueries(String key, String value) {
        bundle.putString(key, value);
        setAuth(bundle);
    }

    private void setAuth(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getQueriesFromBundle() {
        return bundle;
    }

}

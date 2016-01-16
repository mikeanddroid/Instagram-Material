package com.mike.givemewingzz.instagram_codechallenge.service;

import android.os.Build;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mike.givemewingzz.instagram_codechallenge.BuildConfig;
import com.mike.givemewingzz.instagram_codechallenge.utils.AppConstants;
import com.squareup.okhttp.OkHttpClient;

import io.realm.RealmObject;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class BaseClient {

    public static final String TAG = BaseClient.class.getSimpleName();

    // Our static UA used by everything
    public static final String USER_AGENT = initializeUserAgent();

    private BaseClient() {
    }

    private static String initializeUserAgent() {
        return "InstagramDemo/" + BuildConfig.VERSION_NAME + " (Build " + BuildConfig.VERSION_CODE + "; Android " + Build.VERSION.RELEASE + ')';
    }

    public static RetrofitInterface getRetrofitInterface() {
        return LazySIDCInterface.INSTANCE;
    }

    private static class LazySIDCInterface {
        private static final RetrofitInterface INSTANCE = initializeInterface();

        private static RetrofitInterface initializeInterface() {
            // Create the necessary GSON to handle exclusion of Realm pieces
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();

            // Configure OkHttp+AppD
            OkHttpClient client = new OkHttpClient();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setClient(new OkClient(client))
                    .setRequestInterceptor(RequestInterceptor.mIntercepter)
                    .setEndpoint(AppConstants.BASE_URL)
                    .setConverter(new GsonConverter(gson))
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .build();

            RetrofitInterface baseRetrofitInterface = restAdapter.create(RetrofitInterface.class);

            return baseRetrofitInterface;
        }
    }

}

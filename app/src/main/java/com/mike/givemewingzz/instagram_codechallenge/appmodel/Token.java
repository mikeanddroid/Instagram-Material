package com.mike.givemewingzz.instagram_codechallenge.appmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

/**
 * Created by GiveMeWingzz on 1/11/2016.
 */
public class Token {

    private static final String PREF_TOKEN = "pref_token";
    private static final String PREF_TOKEN_EXPIRATION = "pref_token_expiration";

    private static final String PREF_USER_ID = "PREF_USER_ID";
    private static final String PREF_USER_NAME = "PREF_USER_NAME";
    private static final String PREF_USER_FULL_NAME = "PREF_USER_FULL_NAME";
    private static final String PREF_USER_IMAGE = "PREF_USER_IMAGE";
    private static final String PREF_USER_BIO = "PREF_USER_BIO";
    private static final String PREF_USER_MEDIA_COUNT = "PREF_USER_MEDIA_COUNT";
    private static final String PREF_USER_FOLLOWS = "PREF_USER_FOLLOWS";
    private static final String PREF_USER_FOLLOWED_BY = "PREF_USER_FOLLOWED_BY";

    private String userId;

    private String userName;

    private String userFullName;

    private String userProfileImage;

    private String userBio;

    private String userMediaCount;

    private String userFollows;

    private String followedBy;

    protected String accessToken;
    protected Calendar expiration;

    public Token() {
        // Unless we know otherwise, this token expires now
        expiration = Calendar.getInstance();
    }

    public void store(Context context) {

        if (accessToken != null) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString(PREF_TOKEN, accessToken).apply();
            editor.putLong(PREF_TOKEN_EXPIRATION, expiration.getTimeInMillis()).apply();

        }
    }

    public void storeUserInfo(Context context) {

        if (load(context)) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString(PREF_USER_ID, userId).apply();
            editor.putString(PREF_USER_NAME, userName).apply();
            editor.putString(PREF_USER_FULL_NAME, userFullName).apply();
            editor.putString(PREF_USER_IMAGE, userProfileImage).apply();

            editor.putString(PREF_USER_BIO, userBio).apply();
            editor.putString(PREF_USER_MEDIA_COUNT, userMediaCount).apply();
            editor.putString(PREF_USER_FOLLOWS, userFollows).apply();
            editor.putString(PREF_USER_FOLLOWED_BY, followedBy).apply();

        }
    }

    public boolean load(Context context) {

        boolean loaded = false;

        // Retrieve the token
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String prefAccessToken = prefs.getString(PREF_TOKEN, null);

        if (prefAccessToken != null) {

            loaded = true;

            accessToken = prefs.getString(PREF_TOKEN, null);

            long expirationMillis = prefs.getLong(PREF_TOKEN_EXPIRATION, System.currentTimeMillis());
            expiration = Calendar.getInstance();
            expiration.setTimeInMillis(expirationMillis);

        }

        return loaded;

    }

    public SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void clear(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        // Remove everything token related
        editor.remove(PREF_TOKEN);
        editor.remove(PREF_TOKEN_EXPIRATION);

        editor.remove(PREF_USER_ID);
        editor.remove(PREF_USER_NAME);
        editor.remove(PREF_USER_FULL_NAME);
        editor.remove(PREF_USER_IMAGE);
        editor.remove(PREF_USER_BIO);
        editor.remove(PREF_USER_MEDIA_COUNT);
        editor.remove(PREF_USER_FOLLOWS);
        editor.remove(PREF_USER_FOLLOWED_BY);

        editor.apply();

    }

    public static boolean isLoggedIn(Context context) {
        Token token = new Token();
        return token.load(context) && token.isValid();
    }

    /**
     * Determine if the token is set and valid.  To avoid timing out on calls, expiration is
     * shortened by 60 seconds when testing.
     *
     * @return true if the token is set and has not expired; otherwise, false.
     */
    public boolean isValid() {
        return (accessToken != null);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Calendar getExpiration() {
        return expiration;
    }

    public void setExpiration(Calendar expiration) {
        this.expiration = expiration;
    }

    public String getUserId(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF_USER_ID, null);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF_USER_NAME, null);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF_USER_FULL_NAME, null);
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserProfileImage(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF_USER_IMAGE, null);
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getFollowedBy(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF_USER_FOLLOWED_BY, null);
    }

    public void setFollowedBy(String followedBy) {
        this.followedBy = followedBy;
    }

    public String getUserBio(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF_USER_BIO, null);
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserMediaCount(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF_USER_MEDIA_COUNT, null);
    }

    public void setUserMediaCount(String userMediaCount) {
        this.userMediaCount = userMediaCount;
    }

    public String getUserFollows(Context context) {
        return getDefaultSharedPreferences(context).getString(PREF_USER_FOLLOWS, null);
    }

    public void setUserFollows(String userFollows) {
        this.userFollows = userFollows;
    }

    /**
     * An otto bus event indicating that the token has been received
     */
    public static class TokenLoadedEvent {
        public TokenLoadedEvent() {
        }
    }

}

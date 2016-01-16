package com.mike.givemewingzz.instagram_codechallenge.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConstants {

    public static final String BASE_URL = "https://api.instagram.com";

    public static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";
    public static final String TOKENURL = "https://api.instagram.com/oauth/access_token";
    public static final String APIURL = "https://api.instagram.com/v1";
    public static String CALLBACKURL = "http://www.view-unlimited.com";

    public static final String SHARED_PREF_CONSTANT = "SHARED_PREF_CONSTANT";
    public static final String SHARED_PREF_REQUEST_TOKEN_KEY = "SHARED_PREF_REQUEST_TOKEN_KEY";
    public static final String SHARED_PREF_WEB_URL_KEY = "SHARED_PREF_WEB_URL_KEY";
    public static final String SHARED_PREF_BOOLEAN_KEY = "SHARED_PREF_BOOLEAN_KEY";

    public static final String EXTRA_WAITING_FOR_RESULT_KEY = "EXTRA_WAITING_FOR_RESULT_KEY";

    // Constants
    public static final String RESPONSE_TYPE_KEY = "response_type";
    public static final String DISPLAY_KEY = "display";
    public static final String SCOPE = "scope";
    public static final String SCOPE_TYPES = "basic+comments+relationships+likes";

    public static final String MEDIA_URL_KEY = "MEDIA_URL_KEY";
    public static final String MEDIA_CAPTION_KEY = "MEDIA_CAPTION_KEY";
    public static final String MEDIA_URL_ID = "MEDIA_URL_ID";

    public static final String MEDIA_BUNDLE_KEY = "MEDIA_BUNDLE_KEY";

    private static String authURLString;

    public static final String CLIENT_ID = "3a508b397cd94ee997534b9c4770e6ae";

    public static String setAuthURL(String client_id) {

        authURLString = AUTHURL
                + "?client_id="
                + client_id
                + "&redirect_uri="
                + CALLBACKURL
                + "&response_type=token&display=touch&scope=likes+comments+relationships";

        return authURLString;
    }

    public static String[] getHttpAuthCredentials() {
        return null;
    }

    public static SharedPreferences getSharedPreference(Context context) {

        return context.getSharedPreferences(AppConstants.SHARED_PREF_CONSTANT,
                0);

    }

    public static SharedPreferences.Editor getSharedPreferenceEditor(
            Context context) {

        return getSharedPreference(context).edit();

    }

    public static SharedPreferences.Editor setEditorValues(Context context, String key, String value) {

        return getSharedPreferenceEditor(context).putString(key, value);

    }

    public static SharedPreferences.Editor setEditorBooleanValues(Context context, String key, boolean value) {

        return getSharedPreferenceEditor(context).putBoolean(key, value);

    }

}

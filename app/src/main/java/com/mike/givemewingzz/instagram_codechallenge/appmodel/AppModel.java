package com.mike.givemewingzz.instagram_codechallenge.appmodel;

public class AppModel {

    private static boolean isAuthenticated;
    private static String requestToken;
    private static String finalURL;

    public static boolean isAuthenticated() {
        return isAuthenticated;
    }

    public static void setAuthenticated(boolean isAuthenticated) {
        AppModel.isAuthenticated = isAuthenticated;
    }

    public static String getRequestToken() {
        return requestToken;
    }

    public static void setRequestToken(String requestToken) {
        AppModel.requestToken = requestToken;
    }

    public static String getFinalURL() {
        return finalURL;
    }

    public static void setFinalURL(String finalURL) {
        AppModel.finalURL = finalURL;
    }

}

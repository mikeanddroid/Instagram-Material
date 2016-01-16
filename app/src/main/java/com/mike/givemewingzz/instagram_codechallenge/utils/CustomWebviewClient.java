package com.mike.givemewingzz.instagram_codechallenge.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mike.givemewingzz.instagram_codechallenge.appmodel.AppModel;
import com.mike.givemewingzz.instagram_codechallenge.listeners.GenericFinishListener;

import java.util.ArrayList;
import java.util.List;

public class CustomWebviewClient extends WebViewClient {

    private Context context;
    private ProgressDialog mDialog;
    private static List<GenericFinishListener> mListeners;

    public CustomWebviewClient(Context context) {

        this.context = context;
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Loading page...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

    }

    public static void addFinishListener(GenericFinishListener mFinishListener) {

        mListeners = new ArrayList<>();
        mListeners.add(mFinishListener);

    }

    public static void closeActivity() {

        for (GenericFinishListener mFinishListener : mListeners) {
            mFinishListener.onFinishCallback();
        }

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("WEB URL : ", url);
        if (url.startsWith(AppConstants.CALLBACKURL)) {
            String parts[] = url.split("=");
            String request_token = parts[1];  //This is your request token.
            Log.i("REQUEST TOKEN : ", request_token);

            if (!request_token.equals("")) {
                AppConstants.setEditorValues(context, AppConstants.SHARED_PREF_REQUEST_TOKEN_KEY, request_token).commit();
                AppConstants.setEditorBooleanValues(context, AppConstants.SHARED_PREF_BOOLEAN_KEY, true).commit();
                AppConstants.setEditorValues(context, AppConstants.SHARED_PREF_WEB_URL_KEY, url);
                AppModel.setAuthenticated(true);
                AppModel.setFinalURL(url);
                AppModel.setRequestToken(request_token);
            }
            //Add finish here
            closeActivity();

            return true;
        }
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        if (mDialog.isShowing()) {

            mDialog.dismiss();

        }

    }

}

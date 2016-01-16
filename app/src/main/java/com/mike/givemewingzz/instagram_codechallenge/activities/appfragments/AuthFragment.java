package com.mike.givemewingzz.instagram_codechallenge.activities.appfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.listeners.GenericFinishListener;
import com.mike.givemewingzz.instagram_codechallenge.utils.AppConstants;
import com.mike.givemewingzz.instagram_codechallenge.utils.AuthKeys;
import com.mike.givemewingzz.instagram_codechallenge.utils.CustomWebviewClient;


public class AuthFragment extends Fragment implements GenericFinishListener {

    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.web_view, container, false);

        CustomWebviewClient.addFinishListener(this);

        mWebView = (WebView) v.findViewById(R.id.insta_webView);
        mWebView.setWebViewClient(new CustomWebviewClient(getActivity()));
        mWebView.loadUrl(AppConstants.setAuthURL(AuthKeys.CLIENT_ID));// If loading url

        return v;
    }

    @Override
    public void onFinishCallback() {

        getActivity().finish();

    }

}

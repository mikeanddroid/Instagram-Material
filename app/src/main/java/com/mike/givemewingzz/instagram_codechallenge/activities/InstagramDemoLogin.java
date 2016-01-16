package com.mike.givemewingzz.instagram_codechallenge.activities;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.core.CoreActivity;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RobotoTextView;
import com.mike.givemewingzz.instagram_codechallenge.service.BaseClient;
import com.mike.givemewingzz.instagram_codechallenge.service.api_call.GetUser;
import com.mike.givemewingzz.instagram_codechallenge.utils.AppConstants;
import com.mike.givemewingzz.instagram_codechallenge.utils.EventBusSingleton;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GiveMeWingzz on 1/11/2016.
 */
public class InstagramDemoLogin extends CoreActivity {

    public static final String BASE_AUTH_URL = AppConstants.AUTHURL;
    public static String CALLBACKURL = "http://www.view-unlimited.com";
    private static final String ACCESS_TOKEN_COOKIE = "at";
    private static final String USER_TOKEN_COOKIE = "ut";
    public static final String TOKEN_PATH = "/";

    public static final String TAG = InstagramDemoLogin.class.getSimpleName();

    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.wait)
    RobotoTextView waitText;

    private String authInstragramLogin = BASE_AUTH_URL
            + "?client_id=" + AppConstants.CLIENT_ID
            + "&redirect_uri=" + CALLBACKURL
            + "&response_type=token"
            + "&display=touch"
            + "&scope=" + AppConstants.SCOPE_TYPES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instagram_demo_login);

        ButterKnife.bind(this);

        String cookieHost = Uri.parse(authInstragramLogin).getHost();
        configureCookies(cookieHost);

        WebViewClient wvClient = new CIAWebViewClient(this);
        webView.setWebViewClient(wvClient);

        final WebSettings settings = webView.getSettings();
        settings.setUserAgentString(BaseClient.USER_AGENT);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        webView.loadUrl(authInstragramLogin);
    }

    @Override
    protected void onResume() {
        super.onResume();

        EventBusSingleton.register(this);

        if (Token.isLoggedIn(this)) {
            InstagramDemoLogin.this.setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusSingleton.unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Handle back navigation
        if ((KeyEvent.KEYCODE_BACK == keyCode) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle back navigation
        if (item.getItemId() == android.R.id.home && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void captureTokens(String url) {

        Log.e(TAG, "captureTokens : " + url);

        Token token = new Token();

        String[] tokens = url.split("#");

        for (String temp_token : tokens) {

            Log.e(TAG, "captureTokens : temp_token : " + temp_token);

            if (temp_token.startsWith("access_token")) {

                String[] token_parts = temp_token.split("=");
                String _token = token_parts[1];

                token.setAccessToken(_token);

                setRefreshing(false);

                break;

            }

        }


        // Set a short expiration time
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 15);
        token.setExpiration(expiration);

        token.store(this);

    }

    private void configureCookies(String url) {

        final CookieManager cookieManager = CookieManager.getInstance();

        String cookieHeader = cookieManager.getCookie(url);

        if (cookieHeader != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeAllCookies(null);
            } else {
                cookieManager.removeAllCookie();
            }

            // We want to always enter in a not-logged-in state.  If the webview has a cookie indicating
            // it has logged in then it will bypass login and go to the .com site.  Remove that cookie.
            // Also, with CookieManager there is no direct way to remove a cookie.  We just iterate the
            // full set and only add back the cookies we want.
            String[] cookies = cookieHeader.split(";");
            for (String cookie : cookies) {
                if (!cookie.trim().startsWith(ACCESS_TOKEN_COOKIE + '=')) {
                    cookieManager.setCookie(url, cookie);
                }
            }
        }

        // Add the small view cookie.
        // TODO: In a future release, determine the device size (phone v. tablet) and set the correct cookie value for size
        cookieManager.setCookie(url, "idl_icc=s");

        Log.e(TAG, "configureCookies cookie manager : " + cookieManager.getCookie(url));

    }

    public void getProfileInfo() {
        GetUser.getUserSelfInfo(this);
    }

    private class CIAWebViewClient extends WebViewClient {

        private CoreActivity activity;

        public CIAWebViewClient(CoreActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            Log.d(TAG, "onPageStarted : " + "Uri Parse : " + Uri.parse(url).getPath());

            // Display Spinner
            activity.setRefreshing(true);

            // Check for token
            if (TOKEN_PATH.equals(Uri.parse(url).getPath())) {
                // The login page redirects to a POST that returns a JSON block.
                // We don't want the JSON to show to the user so hide the view

                Log.d(TAG, "onPageStarted : URL : " + url);

                waitText.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            Log.d(TAG, "onPageFinished : " + "Uri Parsed : " + Uri.parse(url).getPath());

            if (TOKEN_PATH.equals(Uri.parse(url).getPath())) {

                // Save the tokens
                captureTokens(url);
                getProfileInfo();

            } else {
                // Hide spinner
                // We don't hide spinner if this page has a token, as we want to leave it loading
                // for subsequent api calls. The page will self destruct when those calls hang up.
                // So in that case we never need to hide the spinner.
                setRefreshing(false);
            }

            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            String[] creds = AppConstants.getHttpAuthCredentials();

            if (creds != null) {
                handler.proceed(creds[0], creds[1]);
            } else {
                handler.cancel();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            waitText.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);

            Log.d(TAG, "onReceivedError : " + "Request : " + request);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e(TAG, "onReceivedError : " + "Error Code : " + error.getErrorCode() + " : Error Desc : " + error.getDescription());
            }

            Snackbar.make(webView, getString(R.string.login_error), Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getResources().getColor(R.color.teal5))
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).show();
        }
    }

    @Subscribe
    public void onTokenReceived(GetUser.ProfileSuccess profileSuccess) {
        InstagramDemoLogin.this.setResult(RESULT_OK);
        finish();
    }

}

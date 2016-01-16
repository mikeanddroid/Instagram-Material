package com.mike.givemewingzz.instagram_codechallenge.core;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.activities.InstagramDemoLogin;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaCaptionFrom;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaCaptions;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaImages;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaImagesLow;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaImagesStandard;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaImagesThumb;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaInfo;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaInfoWrapper;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaLocation;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTag;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagCaptionFrom;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagCaptions;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagImages;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagImagesLow;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagImagesStandard;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagImagesThumb;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagLikes;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagUsers;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagWrapper;

import io.realm.Realm;

public class CoreActivity extends AppCompatActivity {

    public static final int LOGIN_REQUEST_CODE = 6102;

    protected Realm realm;

    SwipeRefreshLayout refresh;
    FrameLayout container;

    // Override all the setContentViews to use our layout.
    @Override
    public void setContentView(int layoutResID) {
        View.inflate(this, layoutResID, container);
    }

    @Override
    public void setContentView(View view) {
        container.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        container.addView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        container.addView(view, params);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        super.setContentView(R.layout.refreshable_container);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        container = (FrameLayout) findViewById(R.id.container);

        // Configure our refresher.
        refresh.setColorSchemeResources(R.color.teal1);
        refresh.setEnabled(false);
    }

    @Override
    protected void onDestroy() {

        if (realm != null) {
            realm.close();
        }

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Check flags for home as up
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            boolean isHomeAsUpEnabled = (actionBar.getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP) != 0;
            // If we're managing home as up & user pressed up, go up :-)
            if (isHomeAsUpEnabled && item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Refresh helpers
     */
    public boolean isRefreshing() {
        return refresh.isRefreshing();
    }

    // Done in a callback to UI thread so we can getUserSelfInfo this in onCreate if we wish, or in non ui threads.
    public void setRefreshing(final boolean refreshing) {
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(refreshing);
            }
        });
    }

    public void displaySimpleConfirmSnackBar(View container, String msg) {
        // TODO: There is no design yet for error display.  Update this when that is available.
        Snackbar.make(container, msg, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.orange_11))
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    /*
     * Login Helper.
     */
    public void startLoginActivity() {
        Intent intent = new Intent(this, InstagramDemoLogin.class);
        startActivityForResult(intent, LOGIN_REQUEST_CODE);
    }

    protected void onLoginSucceeded(Token token) {
    }

    protected void onLoginFailed() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If everything is ok, notify children, otherwise finish()
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Try to load token
                Token token = new Token();
                if (token.load(this) && token.isValid()) {
                    onLoginSucceeded(token);
                } else {
                    onLoginFailed();
                }
            } else {
                onLoginFailed();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void cleanAllObjects() {

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        realm.clear(MediaInfoWrapper.class);
        realm.clear(MediaInfo.class);
        realm.clear(MediaCaptions.class);
        realm.clear(MediaCaptionFrom.class);
        realm.clear(MediaImages.class);
        realm.clear(MediaImagesLow.class);
        realm.clear(MediaImagesThumb.class);
        realm.clear(MediaImagesStandard.class);
        realm.clear(MediaLocation.class);

        realm.clear(MediaTagWrapper.class);
        realm.clear(MediaTag.class);
        realm.clear(MediaTagLikes.class);
        realm.clear(MediaTagUsers.class);
        realm.clear(MediaTagCaptions.class);
        realm.clear(MediaTagCaptionFrom.class);
        realm.clear(MediaTagImages.class);
        realm.clear(MediaTagImagesLow.class);
        realm.clear(MediaTagImagesThumb.class);
        realm.clear(MediaTagImagesStandard.class);

        realm.commitTransaction();

        Token token = new Token();
        token.clear(this);

    }

    public void showLogoutFragmentDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.logout_title));
        alertDialog.setMessage(getString(R.string.logout_message));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cleanAllObjects();
                finish();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();

    }

}

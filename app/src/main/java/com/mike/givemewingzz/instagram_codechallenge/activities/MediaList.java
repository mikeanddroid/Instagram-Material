package com.mike.givemewingzz.instagram_codechallenge.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.adapters.MediaAdapter;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaInfo;
import com.mike.givemewingzz.instagram_codechallenge.core.BaseAuthAppCompat;
import com.mike.givemewingzz.instagram_codechallenge.service.api_call.GetUser;
import com.mike.givemewingzz.instagram_codechallenge.utils.AppConstants;
import com.mike.givemewingzz.instagram_codechallenge.utils.EventBusSingleton;
import com.mike.givemewingzz.instagram_codechallenge.utils.image_utils.ImageLoaderUtility;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.squareup.otto.Subscribe;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaList extends BaseAuthAppCompat implements View.OnClickListener, MediaAdapter.EventListener {

    public static final String TAG = MediaList.class.getSimpleName();

    public static final String IN_LIST_TAG = "IN_LIST_TAG";
    public static final String IN_GRID_TAG = "IN_GRID_TAG";
    public static final String LOG_OUT_TAG = "LOG_OUT_TAG";
    public static final String IN_SEARCH_TAG = "IN_SEARCH_TAG";
    public static final String MAIN_ACTION_TAG = "MAIN_ACTION_TAG";

    @Bind(R.id.list)
    public RecyclerView recyclerView;

    @Bind(R.id.profileBlur)
    public ImageView profileBlur;

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    @Bind(R.id.mediaListHolder)
    public ViewGroup viewGroup;

    FloatingActionButton actionButton;
    private MediaAdapter mediaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {

            // Shared element transitions
//            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_transition));
            // End //

            // Trans //
            // Transition for Landing page when it slides in.
            Slide slide = new Slide();
            slide.setDuration(2000);
            getWindow().setEnterTransition(slide);

            // Explode Transition for MediaList when it exits
            getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.landingpage_trans));

            // Trans //

        }

        setContentView(R.layout.recycler_list_layout);

        ButterKnife.bind(this);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);

        linearLayoutManager = new LinearLayoutManager(this);

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.ic_settings_black_36dp);
        imageView.setTag(MAIN_ACTION_TAG);
        imageView.setOnClickListener(this);

        actionButton = new FloatingActionButton.Builder(this).setContentView(imageView).build();

        actionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int eventType = event.getAction();

                switch (eventType) {
                    case MotionEvent.ACTION_DOWN:
                        Fade fade = new Fade();
                        fade.setDuration(1200);

                        if (recyclerView.getVisibility() == View.VISIBLE) {
                            TransitionManager.beginDelayedTransition(recyclerView, fade);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            TransitionManager.beginDelayedTransition(recyclerView, fade);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        break;
                }

                return false;
            }
        });

        ImageView arrangeInList = new ImageView(this);
        arrangeInList.setBackgroundResource(R.drawable.ic_view_day_black_48dp);

        ImageView arrangeInGrid = new ImageView(this);
        arrangeInGrid.setBackgroundResource(R.drawable.ic_view_quilt_black_48dp);

        ImageView logOut = new ImageView(this);
        logOut.setBackgroundResource(R.drawable.ic_face_black_48dp);

        ImageView searchImage = new ImageView(this);
        searchImage.setBackgroundResource(android.R.drawable.ic_search_category_default);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton inList = itemBuilder.setContentView(arrangeInList).build();
        SubActionButton inGrid = itemBuilder.setContentView(arrangeInGrid).build();
        SubActionButton logUserOut = itemBuilder.setContentView(logOut).build();
        SubActionButton inSearch = itemBuilder.setContentView(searchImage).build();

        inList.setTag(IN_LIST_TAG);
        inGrid.setTag(IN_GRID_TAG);
        logUserOut.setTag(LOG_OUT_TAG);
        inSearch.setTag(IN_SEARCH_TAG);

        inList.setOnClickListener(this);
        inGrid.setOnClickListener(this);
        logUserOut.setOnClickListener(this);
        inSearch.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(inList)
                .addSubActionView(inGrid)
                .addSubActionView(logUserOut)
                .addSubActionView(inSearch)
                .attachTo(actionButton)
                .build();

        GetUser.getUserRecentMedia();

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusSingleton.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusSingleton.register(this);
    }

    @Override
    protected void onTokenLoaded(Token token) {
        ImageLoaderUtility.blurAndLoadImage(token.getUserProfileImage(this), profileBlur);
    }

    public void toggleViewVisibe() {
        if (recyclerView.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
        } else if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void loadMedia(RecyclerView.LayoutManager layoutManager) {

        toggleViewVisibe();

        RealmResults<MediaInfo> realmResults = realm.where(MediaInfo.class).findAll();

        mediaAdapter = new MediaAdapter(this, realmResults, true);
        mediaAdapter.setEventListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mediaAdapter);

    }

    @Subscribe
    public void getMedia(GetUser.MediaInfoSuccess mediaInfoSuccess) {
        loadMedia(linearLayoutManager);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v.getTag().equals(IN_LIST_TAG)) {
            EventBusSingleton.post(new OnListEvent());
        } else if (v.getTag().equals(IN_GRID_TAG)) {
            EventBusSingleton.post(new OnGridEvent());
        } else if (v.getTag().equals(LOG_OUT_TAG)) {
            EventBusSingleton.post(new OnLogEvent());
        } else if (v.getTag().equals(IN_SEARCH_TAG)) {
            EventBusSingleton.post(new OnSearchEvent());
        }

    }

    @Override
    public void onItemClick(final View view, MediaInfo mediaInfo) {

        ActivityOptionsCompat optionsCompat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            view.setTransitionName(getString(R.string.next));
            optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MediaList.this, null);
        }

        Intent mediaDetailIntent = new Intent(this, MediaListDetails.class);
        mediaDetailIntent.putExtra(AppConstants.MEDIA_URL_KEY, mediaInfo.getMediaImages().getMediaImagesStandard().getImageURL());
        mediaDetailIntent.putExtra(AppConstants.MEDIA_CAPTION_KEY, mediaInfo.getMediaCaptions().getCaptionText());
//        mediaDetailIntent.putExtra(AppConstants.MEDIA_URL_ID, mediaInfo.getMediaID());

        if (optionsCompat != null) {
//            startActivity(mediaDetailIntent, optionsCompat.toBundle()); // for Shared Transition

        }

        startActivity(mediaDetailIntent);

    }

    public static class OnListEvent {
        public OnListEvent() {
        }
    }

    public static class OnGridEvent {
        public OnGridEvent() {
        }
    }

    public static class OnLogEvent {
        public OnLogEvent() {
        }
    }

    public static class OnSearchEvent {
        public OnSearchEvent() {
        }
    }

    @Subscribe
    public void onListSuccess(OnListEvent object) {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mediaAdapter);

        toggleViewVisibe();

    }

    @Subscribe
    public void onGridSuccess(OnGridEvent object) {
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        recyclerView.setAdapter(mediaAdapter);

        toggleViewVisibe();

    }

    @Subscribe
    public void onLogSuccess(OnLogEvent object) {
        showLogoutFragmentDialog();

        Fade fade = new Fade();
        fade.setDuration(1000);
        TransitionManager.beginDelayedTransition(viewGroup, fade);

    }

    @Subscribe
    public void onSearchSuccess(OnSearchEvent object) {

        Fade fade = new Fade();
        fade.setDuration(1000);
        TransitionManager.beginDelayedTransition(viewGroup, fade);

        Intent intent = new Intent(this, TagSearchPage.class);
        startActivity(intent);

    }

}

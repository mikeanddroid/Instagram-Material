package com.mike.givemewingzz.instagram_codechallenge.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.adapters.MediaTagAdapter;
import com.mike.givemewingzz.instagram_codechallenge.adapters.MediaUserSearchAdapter;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.UsersList;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTag;
import com.mike.givemewingzz.instagram_codechallenge.core.BaseAuthAppCompat;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RobotoTextView;
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
 * Created by GiveMeWingzz on 1/13/2016.
 */
public class TagSearchPage extends BaseAuthAppCompat implements MediaTagAdapter.EventListener, View.OnClickListener, MediaUserSearchAdapter.EventListener {

    private static final String DEFAULT_TAG = "Love";
    private static final String TAG = TagSearchPage.class.getSimpleName();
    private static final String SEARCH_QUERY_KEY = "SEARCH_QUERY_KEY";
    // Views.
    @Bind(R.id.storesSearch)
    public EditText search;

    private MediaTagAdapter mediaTagAdapter;
    private MediaUserSearchAdapter mediaUserSearchAdapter;
    @Bind(R.id.list)
    public RecyclerView recyclerView;

    @Bind(R.id.profileBlur)
    public ImageView profileBlur;

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    @Bind(R.id.search_holder)
    public ViewGroup viewGroup;

    public static final String IN_LIST_TAG = "IN_LIST_TAG";
    public static final String IN_GRID_TAG = "IN_GRID_TAG";
    public static final String LOG_OUT_TAG = "LOG_OUT_TAG";
    public static final String IN_SEARCH_TAG = "IN_SEARCH_TAG";
    public static final String MAIN_ACTION_TAG = "MAIN_ACTION_TAG";

    public static final String SWITCH_KEY = "SWITCH_KEY";

    public FloatingActionButton actionButton;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Bind(R.id.type_switch)
    public Switch adapterSwitch;

    @Bind(R.id.switchTagSearch)
    public RobotoTextView tagSearchText;

    @Bind(R.id.switchUserSearch)
    public RobotoTextView userSearchText;

    private boolean isSwitchOn = true;

    private String searchingTag;
    private String searchingUser;
    private String searchTag;
    private String searchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_search_page);

        ButterKnife.bind(this);

        searchingTag = getResources().getString(R.string.searching_for_tag);
        searchingUser = getResources().getString(R.string.searching_for_user);
        searchTag = getResources().getString(R.string.search_for_tag);
        searchUser = getResources().getString(R.string.search_for_tag);

        preferences = AppConstants.getSharedPreference(this);
        editor = preferences.edit();

        initSearch();

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(this);

        adapterSwitch.setChecked(true);
        //attach a listener to check for changes in state
        adapterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    // "Switch is currently ON";
                    isSwitchOn = true;

                    String queryTemp = search.getText().toString();
                    if (queryTemp.equals("")) {

                        tagSearchText.setTextColor(getResources().getColor(R.color.white));
                        tagSearchText.setText(searchTag);

                        userSearchText.setTextColor(getResources().getColor(R.color.colorAccent));
                        userSearchText.setText(searchingUser);

                        queryTemp = DEFAULT_TAG;
                        queryTemp.trim();
                        GetUser.getSearchedUserByName(TagSearchPage.this, queryTemp);
                    }

                } else {
                    // "Switch is currently OFF");
                    isSwitchOn = false;

                    String query = search.getText().toString();
                    query.trim();

                    if (query.contains(" ")) {
                        if (query.equals("")) {
                            query = DEFAULT_TAG;
                            query.trim();
                            GetUser.getSearchedUserByName(TagSearchPage.this, query);
                        }
                    } else {

                        tagSearchText.setTextColor(getResources().getColor(R.color.colorAccent));
                        tagSearchText.setText(searchingTag);

                        userSearchText.setTextColor(getResources().getColor(R.color.white));
                        userSearchText.setText(searchUser);

                        if (query.equals("")) {
                            query = DEFAULT_TAG;
                        }
                        GetUser.getMediaForTagName(TagSearchPage.this, query);
                    }

                }

                editor.putBoolean(SWITCH_KEY, isSwitchOn).apply();

            }
        });

        // Setting fab //

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.ic_settings_black_36dp);
        imageView.setTag(MAIN_ACTION_TAG);

        actionButton = new FloatingActionButton.Builder(this).setContentView(imageView).build();

        actionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int eventType = event.getAction();

                switch (eventType) {
                    case MotionEvent.ACTION_DOWN:
                        toggleAnimation(viewGroup);
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
        searchImage.setBackgroundResource(R.drawable.ic_turned_in_black_48dp);

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

        // End //

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

    private void initSearch() {

        // Set up search.
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //Close keyboard & clear focus
                    search.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    String searchQuery = v.getText().toString();
                    if (!TextUtils.isEmpty(searchQuery)) {
                        // Load the data
                        getMediaForTagName(searchQuery);

                    } else {
                        getMediaForTagName(DEFAULT_TAG);
                    }

                    //We caught the event
                    return true;
                }
                return false;
            }
        });

    }

    public void loadTagData(RecyclerView.LayoutManager layoutManager) {
        RealmResults<MediaTag> realmResults = realm.where(MediaTag.class).findAll();
        mediaTagAdapter = new MediaTagAdapter(this, realmResults, true);
        mediaTagAdapter.setEventListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mediaTagAdapter);
    }

    public void loadUserData(RecyclerView.LayoutManager layoutManager) {
        RealmResults<UsersList> realmResults = realm.where(UsersList.class).findAll();

        for (UsersList usersList : realmResults) {
            Log.d(TAG, "User Names : " + usersList.getUserName());
        }

        mediaUserSearchAdapter = new MediaUserSearchAdapter(this, realmResults, true);
        mediaUserSearchAdapter.setEventListener(this);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        recyclerView.setAdapter(mediaUserSearchAdapter);
    }

    @Subscribe
    public void onTagSearchSuccess(GetUser.MediaTagInfoSuccess object) {
        loadTagData(linearLayoutManager);
        setRefreshing(false);
    }

    @Subscribe
    public void onTagSearchFailure(GetUser.MediaTagInfoFailure object) {
        setRefreshing(false);
        displaySimpleConfirmSnackBar(recyclerView, object.getErrorMessage());

    }

    @Subscribe
    public void onSearchUserSuccess(GetUser.ProfileSearchSuccess object) {
        loadUserData(linearLayoutManager);
        setRefreshing(false);
    }

    @Subscribe
    public void onSearchUserSuccess(GetUser.ProfileSearchFailure object) {
        setRefreshing(false);
        displaySimpleConfirmSnackBar(recyclerView, object.getErrorMessage());
    }

    private void getMediaForTagName(String query) {
        setRefreshing(true);

        if (!query.matches("^[0-9]{5}$")) {
            editor.putString(SEARCH_QUERY_KEY, query).apply();
            boolean isSwitchOn = preferences.getBoolean(SWITCH_KEY, false);

            if (isSwitchOn) {

                query.trim();
                GetUser.getSearchedUserByName(TagSearchPage.this, query);

            } else {

                if (query.contains(" ")) {
                    if (query.equals("")) {
                        query = DEFAULT_TAG;
                        query.trim();
                        GetUser.getSearchedUserByName(TagSearchPage.this, query);
                    }
                } else {
                    GetUser.getMediaForTagName(TagSearchPage.this, query);
                }

            }

        }

    }

    @Override
    protected void onTokenLoaded(Token token) {
        ImageLoaderUtility.blurAndLoadImage(token.getUserProfileImage(this), profileBlur);
    }

    @Override
    public void onItemClick(View view, MediaTag mediaTag) {
        Intent mediaDetailIntent = new Intent(this, MediaListDetails.class);
        mediaDetailIntent.putExtra(AppConstants.MEDIA_URL_KEY, mediaTag.getMediaImages().getMediaTagImagesStandard().getImageURL());
        mediaDetailIntent.putExtra(AppConstants.MEDIA_CAPTION_KEY, mediaTag.getMediaCaptions().getCaptionText());
        startActivity(mediaDetailIntent);
    }

    public void toggleViewVisibe() {
        if (recyclerView.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
        } else if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void toggleAnimation(ViewGroup viewGroup) {
        Fade fade = new Fade();
        fade.setDuration(1200);

        TransitionManager.beginDelayedTransition(viewGroup, fade);
    }

    @Subscribe
    public void onListSuccess(OnListEvent object) {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mediaTagAdapter);

        toggleViewVisibe();

    }

    @Subscribe
    public void onGridSuccess(OnGridEvent object) {
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        recyclerView.setAdapter(mediaTagAdapter);

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
    public void onItemClick(View view, UsersList usersList) {

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

}

package com.mike.givemewingzz.instagram_codechallenge.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.adapters.TagsAdapter;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.core.BaseAuthAppCompat;
import com.mike.givemewingzz.instagram_codechallenge.service.api_call.GetUser;
import com.mike.givemewingzz.instagram_codechallenge.utils.AppUtils;
import com.mike.givemewingzz.instagram_codechallenge.utils.EventBusSingleton;
import com.mike.givemewingzz.instagram_codechallenge.utils.image_utils.ImageLoaderUtility;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GiveMeWingzz on 1/15/2016.
 */
public class TagsPage extends BaseAuthAppCompat {

    private static final String TAG = TagsPage.class.getSimpleName();
    @Bind(R.id.profileBlur)
    public ImageView profileBlur;

    @Bind(R.id.list)
    public RecyclerView recyclerView;

    @Bind(R.id.skipTagPageHolder)
    public LinearLayout skipHolder;

    private TagsAdapter adapter;

    AppUtils.HashClass hashClass;

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_list_tag_layout);

        ButterKnife.bind(this);

        init();

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

    private void init() {

        hashClass = new AppUtils.HashClass();
        List<String> hashTags = hashClass.getHashTagsList();

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        adapter = new TagsAdapter(this, hashTags);

        recyclerView.setAdapter(adapter);

        skipHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetUser.getUserFollows();
            }
        });

    }

    @Override
    protected void onTokenLoaded(Token token) {
        ImageLoaderUtility.blurAndLoadImage(token.getUserProfileImage(this), profileBlur);
    }

    @Subscribe
    public void onUserFollowersSuccess(GetUser.UserFollowsSuccess userFollowsSuccess) {

        Log.d(TAG, "UserFollowsSuccess follows : " + userFollowsSuccess.getMediaInfo().size());

    }

}

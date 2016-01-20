package com.mike.givemewingzz.instagram_codechallenge.adapters;

/**
 * Created by GiveMeWingzz on 1/19/2016.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.user.UserFollows;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RobotoTextView;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RoundedImageView;
import com.mike.givemewingzz.instagram_codechallenge.utils.image_utils.ImageLoaderUtility;

import io.realm.RealmResults;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MainMediaAdapter extends RealmBaseRecyclerViewAdapter<UserFollows, MainMediaAdapter.UserSearchViewHolder> {

    RealmResults<UserFollows> list;

    public Context context;
    public EventListener eventListener;
    public Token token;

    public MainMediaAdapter(Context context, RealmResults<UserFollows> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.list = realmResults;
        this.context = context;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public UserSearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_mainmedia_items, viewGroup, false);
        UserSearchViewHolder mediaViewHolder = new UserSearchViewHolder(v);
        return mediaViewHolder;
    }

    @Override
    public void onBindViewHolder(UserSearchViewHolder holder, int position) {

        final UserFollows userFollows = list.get(position);

        String user_name = userFollows.getUserName();
        String user_image = userFollows.getUserProfilePicture();
        String user_id = userFollows.getUserId();

        holder.user_header.setText(user_name);

        ImageLoaderUtility.loadImage(user_image, holder.user_image);

        Animation animation;
        animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        holder.card_view.startAnimation(animation);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserSearchViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView user_image;
        ImageView user_media;
        RobotoTextView user_header;
        CardView card_view;

        UserSearchViewHolder(View itemView) {
            super(itemView);
            user_header = (RobotoTextView) itemView.findViewById(R.id.user_header);
            user_image = (RoundedImageView) itemView.findViewById(R.id.user_image);
            user_media = (ImageView) itemView.findViewById(R.id.user_media);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public interface EventListener {
        void onItemClick(final View view, String profileURL);
    }

}


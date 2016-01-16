package com.mike.givemewingzz.instagram_codechallenge.adapters;

/**
 * Created by GiveMeWingzz on 1/14/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.UsersList;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RobotoTextView;
import com.mike.givemewingzz.instagram_codechallenge.utils.image_utils.ImageLoaderUtility;

import io.realm.RealmResults;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaUserSearchAdapter extends RealmBaseRecyclerViewAdapter<UsersList, MediaUserSearchAdapter.UserSearchViewHolder> {

    public RealmResults<UsersList> usersLists;
    public Context context;
    public EventListener eventListener;

    public MediaUserSearchAdapter(Context context, RealmResults<UsersList> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.usersLists = realmResults;
        this.context = context;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public UserSearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_users_likes_item, viewGroup, false);
        UserSearchViewHolder mediaViewHolder = new UserSearchViewHolder(v);
        return mediaViewHolder;
    }

    @Override
    public void onBindViewHolder(UserSearchViewHolder holder, int position) {

        final UsersList usersList = getItem(position);

        String userName = ""+usersList.getUserName();
        String userFullName = usersList.getUserFullName();
        String mediaURL = usersList.getUserProfilePicture();

        holder.userName.setText(userName);
        holder.userFullName.setText(userFullName);

        ImageLoaderUtility.loadImage(mediaURL, holder.userImage);

        Animation animation;
        animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        holder.userHolderView.startAnimation(animation);

        holder.userHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                eventListener.onItemClick(v, usersList);
            }
        });

    }

    @Override
    public UsersList getItem(int i) {
        return usersLists.get(i);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return usersLists.size();
    }

    public static class UserSearchViewHolder extends RecyclerView.ViewHolder {
        RobotoTextView userName;
        RobotoTextView userFullName;
        ImageView userImage;
        LinearLayout userHolderView;

        UserSearchViewHolder(View itemView) {
            super(itemView);
            userName = (RobotoTextView) itemView.findViewById(R.id.userName);
            userFullName = (RobotoTextView) itemView.findViewById(R.id.userFullName);
            userImage = (ImageView) itemView.findViewById(R.id.userProfileImage);
            userHolderView = (LinearLayout) itemView.findViewById(R.id.userHolder);
        }
    }

    public interface EventListener {
        void onItemClick(final View view, UsersList usersList);
    }

}

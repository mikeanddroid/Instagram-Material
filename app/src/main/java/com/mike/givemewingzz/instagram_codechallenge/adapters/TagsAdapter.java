package com.mike.givemewingzz.instagram_codechallenge.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RobotoTextView;

import java.util.List;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.UserSearchViewHolder> {

    List<String> popularHashTags;

    public Context context;
    public EventListener eventListener;
    public Token token;

    public TagsAdapter(Context context, List<String> popularHashTags) {
        this.popularHashTags = popularHashTags;
        this.context = context;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public UserSearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_hashtags_items, viewGroup, false);
        UserSearchViewHolder mediaViewHolder = new UserSearchViewHolder(v);
        return mediaViewHolder;
    }

    @Override
    public void onBindViewHolder(UserSearchViewHolder holder, int position) {

        final String hashVal = popularHashTags.get(position);

        holder.tagName.setText(hashVal);

        Animation animation;
        animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        holder.tagsHolderView.startAnimation(animation);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return popularHashTags.size();
    }

    public static class UserSearchViewHolder extends RecyclerView.ViewHolder {
        RobotoTextView tagName;
        FrameLayout tagsHolderView;

        UserSearchViewHolder(View itemView) {
            super(itemView);
            tagName = (RobotoTextView) itemView.findViewById(R.id.tagsName);
            tagsHolderView = (FrameLayout) itemView.findViewById(R.id.tagsHolder);
        }
    }

    public interface EventListener {
        void onItemClick(final View view, String profileURL);
    }

}

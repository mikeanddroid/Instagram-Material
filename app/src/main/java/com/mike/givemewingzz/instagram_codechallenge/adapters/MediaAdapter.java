package com.mike.givemewingzz.instagram_codechallenge.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaInfo;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaLikes;
import com.mike.givemewingzz.instagram_codechallenge.utils.image_utils.ImageLoaderUtility;

import io.realm.RealmResults;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaAdapter extends RealmBaseRecyclerViewAdapter<MediaInfo, MediaAdapter.MediaViewHolder> {

    public RealmResults<MediaInfo> infoWrappers;
    public Context context;
    public EventListener eventListener;
    public final String heartUnif = "\u2764 ";

    public MediaAdapter(Context context, RealmResults<MediaInfo> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.infoWrappers = realmResults;
        this.context = context;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_items, viewGroup, false);
        MediaViewHolder mediaViewHolder = new MediaViewHolder(v);
        return mediaViewHolder;
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {

        final MediaInfo mediaInfo = getItem(position);
        final MediaLikes mediaLikes = mediaInfo.getMediaLikes();

        String captionText = mediaInfo.getMediaCaptions().getCaptionText();
        String likes = heartUnif + mediaLikes.getLikesCount() + " Likes";
        String mediaURL = mediaInfo.getMediaImages().getMediaImagesStandard().getImageURL();

        holder.mediaCaption.setText(captionText);
        holder.mediaLikes.setText(likes);
        ImageLoaderUtility.loadImage(mediaURL, holder.mediaImage);

        Animation animation;
        animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        holder.mediaHolderView.startAnimation(animation);

        holder.mediaHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                eventListener.onItemClick(v, mediaInfo);
            }
        });

    }

    @Override
    public MediaInfo getItem(int i) {
        return infoWrappers.get(i);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return infoWrappers.size();
    }

    public static class MediaViewHolder extends RecyclerView.ViewHolder {
        TextView mediaCaption;
        TextView mediaLikes;
        ImageView mediaImage;
        LinearLayout mediaHolderView;

        MediaViewHolder(View itemView) {
            super(itemView);
            mediaHolderView = (LinearLayout) itemView.findViewById(R.id.mediaHolder);
            mediaCaption = (TextView) itemView.findViewById(R.id.item_caption);
            mediaLikes = (TextView) itemView.findViewById(R.id.item_media_likes);
            mediaImage = (ImageView) itemView.findViewById(R.id.item_media);
        }
    }

    public interface EventListener {
        void onItemClick(final View view, MediaInfo mediaInfo);
    }

}

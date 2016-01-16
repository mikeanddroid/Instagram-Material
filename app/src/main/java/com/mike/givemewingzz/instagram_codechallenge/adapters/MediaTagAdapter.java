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

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTag;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RobotoTextView;
import com.mike.givemewingzz.instagram_codechallenge.utils.image_utils.ImageLoaderUtility;

import io.realm.RealmResults;

/**
 * Created by GiveMeWingzz on 1/12/2016.
 */
public class MediaTagAdapter extends RealmBaseRecyclerViewAdapter<MediaTag, MediaTagAdapter.MediaTagViewHolder> {

    public RealmResults<MediaTag> mediaTags;
    public Context context;
    public EventListener eventListener;

    public MediaTagAdapter(Context context, RealmResults<MediaTag> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.mediaTags = realmResults;
        this.context = context;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public MediaTagViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_tag_items, viewGroup, false);
        MediaTagViewHolder mediaViewHolder = new MediaTagViewHolder(v);
        return mediaViewHolder;
    }

    @Override
    public void onBindViewHolder(MediaTagViewHolder holder, int position) {

        final MediaTag mediaInfo = getItem(position);

        String captionText = mediaInfo.getMediaCaptions().getCaptionText();
        String likes = "\u2766 " + mediaInfo.getMediaTagLikes().getLikesCount() + " Likes";
        String mediaURL = mediaInfo.getMediaImages().getMediaTagImagesStandard().getImageURL();

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
    public MediaTag getItem(int i) {
        return mediaTags.get(i);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mediaTags.size();
    }

    public static class MediaTagViewHolder extends RecyclerView.ViewHolder {
        RobotoTextView mediaCaption;
        RobotoTextView mediaLikes;
        ImageView mediaImage;
        LinearLayout mediaHolderView;

        MediaTagViewHolder(View itemView) {
            super(itemView);
            mediaHolderView = (LinearLayout) itemView.findViewById(R.id.mediaHolder);
            mediaCaption = (RobotoTextView) itemView.findViewById(R.id.item_caption);
            mediaLikes = (RobotoTextView) itemView.findViewById(R.id.item_likes);
            mediaImage = (ImageView) itemView.findViewById(R.id.item_media);
        }
    }

    public interface EventListener {
        void onItemClick(final View view, MediaTag mediaTag);
    }

}

package com.mike.givemewingzz.instagram_codechallenge.utils.image_utils;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Image Loading utility to load images.
 */
public class ImageLoaderUtility {

    private static final DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

    private static final DisplayImageOptions blurOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .preProcessor(new BlurBitmapProcessor())
            .build();

    public static void loadImage(String imageURI, ImageView imageView) {

        ImageLoader.getInstance().displayImage(imageURI, imageView, options);

    }

    public static void blurAndLoadImage(String imageURI, ImageView imageView) {
        ImageLoader.getInstance().displayImage(imageURI, imageView, blurOptions);
    }

}

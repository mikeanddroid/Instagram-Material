package com.mike.givemewingzz.instagram_codechallenge.core;

import android.app.Application;
import android.content.Context;

import com.mike.givemewingzz.instagram_codechallenge.utils.DBHelper;
import com.mike.givemewingzz.instagram_codechallenge.utils.EventBusSingleton;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.squareup.otto.Bus;

import io.realm.Realm;

/**
 * Created by GiveMeWingzz on 1/11/2016.
 */
public class InstagramDemoApplication extends Application {

    public static Context context;
    public static InstagramDemoApplication application;

    // Hold reference to event bus.
    Bus bus = EventBusSingleton.getBus();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = this.getApplicationContext();

        // Setup Realm for database interaction.
        Realm.setDefaultConfiguration(DBHelper.getRealmConfig(this));

        initImageLoader(context);
    }

    // Inititalizing Imageloader
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.MAX_PRIORITY)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCacheSizePercentage(25).threadPoolSize(5)
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        L.writeLogs(false);
    }

    public static InstagramDemoApplication getInstance() {
        return application;
    }

}

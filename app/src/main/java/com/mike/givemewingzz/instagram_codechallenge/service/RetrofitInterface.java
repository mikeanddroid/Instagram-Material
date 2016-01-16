package com.mike.givemewingzz.instagram_codechallenge.service;

import com.mike.givemewingzz.instagram_codechallenge.appmodel.UserWrapper;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.UsersListWrapper;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaInfoWrapper;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagWrapper;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by GiveMeWingzz on 1/7/2016.
 */
public interface RetrofitInterface {

    @GET("/v1/users/self/")
    public void getSelfUserInfo(Callback<UserWrapper> cb);

    @GET("/v1/users/{user-id}/")
    public void getUserInfoByID(@Path("user-id") String user_id, Callback<UserWrapper> cb);

    @GET("/v1/users/self/media/recent/")
    public void getUserRecentMedia(Callback<MediaInfoWrapper> cb);

    @GET("/v1/users/{user-id}/media/recent/")
    public void getRecentUserMediaInfo(@Path("user-id") String user_id, Callback<UserWrapper> cb);

    @GET("/v1/users/self/media/liked")
    public void getRecentMediaLikedInfo(Callback<UserWrapper> cb);

    @GET("/v1/users/search")
    public void getSearchedUserByName(@Query("q") String userName, Callback<UsersListWrapper> cb);

    // Media //

    @GET("/v1/media/{media-id}")
    public void getMediaInfoWithID(@Path("media-id") String media_id, Callback<MediaInfoWrapper> cb);


    // Tags //
    @GET("/v1/tags/{tag-name}/media/recent")
    public void getMediaForTagName(@Path("tag-name") String tag_name, Callback<MediaTagWrapper> cb);

}

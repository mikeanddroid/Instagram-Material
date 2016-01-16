package com.mike.givemewingzz.instagram_codechallenge.service;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.mime.TypedOutput;

public interface TokenInterface {

    //www- endpoint
    @POST("/identity/signout")
    void logout(@Header("token") String token, @Body TypedOutput empty, Callback<Object> cb);

}

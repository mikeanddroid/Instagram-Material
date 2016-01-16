package com.mike.givemewingzz.instagram_codechallenge.service;

import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.core.InstagramDemoApplication;

public class RequestInterceptor {

    public static final String TAG = RequestInterceptor.class.getSimpleName();
    public static final retrofit.RequestInterceptor mIntercepter = new BaseUrlInterceptor();

    private static class BaseUrlInterceptor extends BaseRequestInterceptor {

        @Override
        public void intercept(retrofit.RequestInterceptor.RequestFacade request) {

            Token token = new Token();
            token.load(InstagramDemoApplication.getInstance().getApplicationContext());

            request.addEncodedQueryParam("access_token", token.getAccessToken());
        }
    }

}

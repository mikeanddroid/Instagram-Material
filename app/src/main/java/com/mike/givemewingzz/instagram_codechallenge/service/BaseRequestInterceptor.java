package com.mike.givemewingzz.instagram_codechallenge.service;

import retrofit.RequestInterceptor;

public abstract class BaseRequestInterceptor implements RequestInterceptor {

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestFacade}.
     *
     * @param request
     */
    @Override
    public void intercept(RequestFacade request) {
        // Subclass Can modify the request when overriding this method.
    }
}

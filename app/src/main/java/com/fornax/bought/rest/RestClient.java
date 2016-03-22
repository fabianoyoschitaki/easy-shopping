package com.fornax.bought.rest;

import com.fornax.bought.utils.Constants;
import com.fornax.bought.utils.JSONUtil;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by root on 03/12/15.
 */
public class RestClient {
    private RestAPI restApi;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.REST_BASE_IBOUGHT_URL)
                .setConverter(new GsonConverter(JSONUtil.getGSONInstance()))
                        //.setRequestInterceptor(new SessionRequestInterceptor()) //não sei ainda
                .build();

        restApi = restAdapter.create(RestAPI.class); //creating a service for adapter with our GET class
    }

    public RestAPI getRestAPI() {
        return restApi;
    }
}

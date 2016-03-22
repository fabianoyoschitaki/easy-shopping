package com.fornax.bought.rest;

import com.fornax.bought.utils.Constants;
import com.fornax.bought.utils.JSONUtil;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Administrador on 10/01/2016.
 */
public class WSRestService {
    private RestAPI restApi;

    public WSRestService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.REST_BASE_IBOUGHT_URL)
                .setConverter(new GsonConverter(JSONUtil.getGSONInstance()))
                .build();

        restApi = restAdapter.create(RestAPI.class);
    }

    public RestAPI getRestAPI() {
        return restApi;
    }
}

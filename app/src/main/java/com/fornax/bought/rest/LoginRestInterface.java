package com.fornax.bought.rest;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface LoginRestInterface {

    @GET("/rest/login/{email}/{password}")
    Call<User> getUser(@Path("email") String username, @Path("password") String password);

}
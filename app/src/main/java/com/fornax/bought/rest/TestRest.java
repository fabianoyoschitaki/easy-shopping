package com.fornax.bought.rest;

import java.io.IOException;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Administrador on 23/11/2015.
 */
public class TestRest {


    public static final String BASE_URL = "http://localhost:8080/bought";

    public void logar(String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginRestInterface ap  = retrofit.create(LoginRestInterface.class);
        Call<User> call = ap.getUser(email, password);

        try {
            Response userResponse = call.execute();
            System.out.println("deuu");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

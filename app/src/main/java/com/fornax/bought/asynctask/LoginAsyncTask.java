package com.fornax.bought.asynctask;
import android.os.AsyncTask;

import com.fornax.bought.rest.LoginRestInterface;
import com.fornax.bought.rest.User;

import java.io.IOException;
import java.net.URL;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginAsyncTask{

//    public static final String BASE_URL = "http://localhost:8080/bought";
//
//    protected Long doInBackground(URL... urls) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        LoginRestInterface ap  = retrofit.create(LoginRestInterface.class);
//        Call<User> call = ap.getUser(email, password);
//
//        try {
//            Response userResponse = call.execute();
//            System.out.println("deuu");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    protected void onProgressUpdate(Integer... progress) {
//
//    }
//
//    protected void onPostExecute(Long result) {
//
//    }
}
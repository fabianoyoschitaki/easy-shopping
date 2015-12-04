package com.fornax.bought.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

/**
 * Created by fabiano on 17/11/15.
 */
public class SharedPreferencesUtil {

    private static final String MyPREFERENCES = "MyPrefs" ;

    // para coisas simples como String, Integer, primitivos
    private SharedPreferences sharedpreferences;

    public SharedPreferencesUtil(ContextWrapper ctx){
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public String getString(String key){
        return sharedpreferences.getString(key, null);
    }

    public boolean contains(String key){
        return sharedpreferences.contains(key);
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void remove(String key){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}

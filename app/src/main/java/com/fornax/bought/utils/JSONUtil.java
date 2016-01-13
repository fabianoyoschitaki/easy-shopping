package com.fornax.bought.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 03/12/15.
 */
public class JSONUtil {
    private static final String TAG = JSONUtil.class.getName();
    private static final Gson gson;

    // Esse código serve para serializar e desserializar Date como long
    static {
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        });

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Calendar.class, new JsonDeserializer<Calendar>() {
            @Override
            public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(json.getAsJsonPrimitive().getAsLong()));
                return calendar;
            }
        });

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Calendar.class, new JsonSerializer<Calendar>() {
            @Override
            public JsonElement serialize(Calendar src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime().getTime());
            }
        });

        gson = builder.create();
    }

    public static Gson getGSONInstance(){
        return gson;
    }

    public static String toJSON(Object object){
        String retorno = gson.toJson(object);
        Log.d(TAG, retorno);
        return retorno;
    }
}

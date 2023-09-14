package com.example.shopp.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofit {

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        //////////////////////////////////////////////////////////////
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .build();
        //////////////////////////////////////////////////////////////
        Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("https://shopapp.onrender.com/") //baseUrl("https://serverdiscovery.onrender.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
        //////////////////////////////////////////////////////////////
    }
}

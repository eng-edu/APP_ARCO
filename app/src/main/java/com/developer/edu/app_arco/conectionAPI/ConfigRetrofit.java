package com.developer.edu.app_arco.conectionAPI;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ConfigRetrofit {

    public static String URL_BASE1 = "http://192.168.10.120:8080";
    public static String URL_BASE = "http://191.252.186.65:8080";

    public static RetrofitService getService() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URL_BASE)
                .client(okHttpClient)
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        return service;
    }


}

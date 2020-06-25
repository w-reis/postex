package com.postex.app.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    private String API_BASE_URL = "http://10.0.2.2:3333/";

    public Retrofit createRequest(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        return retrofit;
    }
}

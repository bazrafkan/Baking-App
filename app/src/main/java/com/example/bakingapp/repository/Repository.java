package com.example.bakingapp.repository;

import com.example.bakingapp.network.WebService;
import com.example.bakingapp.utility.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    Retrofit retrofit;
    WebService webService;

    public Repository() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.webService = retrofit.create(WebService.class);
    }
}

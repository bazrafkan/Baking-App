package com.example.bakingapp.network;

import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utility.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET(Constant.PATH_URL)
    Call<List<Recipe>> getListRecipe();
}

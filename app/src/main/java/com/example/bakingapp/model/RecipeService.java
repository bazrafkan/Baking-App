package com.example.bakingapp.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET(Recipe.pathUrl)
    Call<List<Recipe>> listRecipe();
}

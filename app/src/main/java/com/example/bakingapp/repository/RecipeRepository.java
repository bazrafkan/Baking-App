package com.example.bakingapp.repository;

import com.example.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository extends Repository {
    public interface AsyncRecipeRepository {
        void onResponse(List<Recipe> recipes);

        void onFailure(Throwable t);
    }

    AsyncRecipeRepository asyncRecipeRepository;

    public void setAsyncRecipeRepository(AsyncRecipeRepository asyncRecipeRepository) {
        this.asyncRecipeRepository = asyncRecipeRepository;
    }

    public void getRecipes() {
        webService.getListRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (asyncRecipeRepository != null) {
                    asyncRecipeRepository.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                if (asyncRecipeRepository != null) {
                    asyncRecipeRepository.onFailure(t);
                }
            }
        });
    }
}

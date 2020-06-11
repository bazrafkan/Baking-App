package com.example.bakingapp.task;

import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.RecipeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeTask {
    public interface AsyncRecipeTaskResult {
        void onPreExecute();

        void onPostExecute(List<Recipe> recipeList);

        void onFailureExecute(Throwable throwable);
    }

    public AsyncRecipeTaskResult delegate;

    public RecipeTask(AsyncRecipeTaskResult delegate) {
        this.delegate = delegate;
    }

    public void execute() {
        delegate.onPreExecute();
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(Recipe.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = retrofit.create(RecipeService.class);
        Call<List<Recipe>> callRecipe = service.listRecipe();
        callRecipe.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                delegate.onPostExecute(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                delegate.onFailureExecute(t);
            }
        });
    }
}

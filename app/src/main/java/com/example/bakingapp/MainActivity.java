package com.example.bakingapp;

import com.example.bakingapp.adapter.RecipeAdapter;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.task.RecipeTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RecipeTask.AsyncRecipeTaskResult,
        SwipeRefreshLayout.OnRefreshListener,
        RecipeAdapter.RecipeAdapterOnClick {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Recipe> mRecipeList;
    private RecipeTask mRecipeTask;
    private RecyclerView mRecyclerView;
    private static final String LIST_RECIPE_KEY = "recipe_list_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setTitle("Baking Time");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        mSwipeRefreshLayout = findViewById(R.id.sr_recipe_list);
        mRecyclerView = findViewById(R.id.rv_recipe_list);

        mRecipeTask = new RecipeTask(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(LIST_RECIPE_KEY)) {
            Serializable serializable = savedInstanceState.getSerializable(LIST_RECIPE_KEY);
            try {
                mRecipeList = (ArrayList) serializable;
                showListRecipe();
            } catch (Exception e) {
                getListRecipe();
            }
        } else {
            getListRecipe();
        }
    }

    private void showListRecipe() {
        RecipeAdapter mRecipeAdapter = new RecipeAdapter(this, mRecipeList);
        mRecipeAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        int list_recipe_column_item = getResources().getInteger(R.integer.list_recipe_column_item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, list_recipe_column_item);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void getListRecipe() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecipeTask.execute();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mRecipeList != null && mRecipeList.size() > 0) {
            outState.putSerializable(LIST_RECIPE_KEY, new ArrayList<>(mRecipeList));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        mSwipeRefreshLayout.setRefreshing(false);
        showListRecipe();
    }

    @Override
    public void onFailureExecute(Throwable throwable) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mRecipeTask.execute();
    }

    @Override
    public void onClick(int position) {
        Recipe recipe = mRecipeList.get(position);
        Toast.makeText(this, "Recipe: " + recipe.getName(), Toast.LENGTH_SHORT).show();
    }
}

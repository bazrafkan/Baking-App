package com.example.bakingapp;

import com.example.bakingapp.adapter.RecipeAdapter;
import com.example.bakingapp.adapter.RecipeAdapterOnClick;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.repository.RecipeRepository;
import com.example.bakingapp.utility.Constant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RecipeRepository.AsyncRecipeRepository,
        SwipeRefreshLayout.OnRefreshListener,
        RecipeAdapterOnClick {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Recipe> mRecipeList;
    private RecipeRepository mRecipeRepository;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.backing_time));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        mSwipeRefreshLayout = findViewById(R.id.sr_recipe_list);
        mRecyclerView = findViewById(R.id.rv_recipe_list);

        mRecipeRepository = new RecipeRepository();
        mRecipeRepository.setAsyncRecipeRepository(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(Constant.LIST_RECIPE_KEY)) {
            Serializable serializable = savedInstanceState.getSerializable(Constant.LIST_RECIPE_KEY);
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
        mRecipeRepository.getRecipes();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mRecipeList != null && mRecipeList.size() > 0) {
            outState.putSerializable(Constant.LIST_RECIPE_KEY, new ArrayList<>(mRecipeList));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        mRecipeRepository.getRecipes();
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailsRecipeActivity.class);
        intent.putExtra(Constant.SELECTED_RECIPE_KEY, mRecipeList.get(position));
        startActivity(intent);
    }

    @Override
    public void onResponse(List<Recipe> recipes) {
        mRecipeList = recipes;
        mSwipeRefreshLayout.setRefreshing(false);
        showListRecipe();
    }

    @Override
    public void onFailure(Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

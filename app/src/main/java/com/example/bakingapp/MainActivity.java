package com.example.bakingapp;

import com.example.bakingapp.adapter.RecipeAdapter;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.task.RecipeTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RecipeTask.AsyncRecipeTaskResult,
        SwipeRefreshLayout.OnRefreshListener,
        RecipeAdapter.RecipeAdapterOnClick {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Recipe> mRecipeList;
    private RecipeTask mRecipeTask;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = findViewById(R.id.sr_recipe_list);
        mRecyclerView = findViewById(R.id.rv_recipe_list);

        mRecipeTask = new RecipeTask(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mRecipeTask.execute();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        RecipeAdapter mRecipeAdapter = new RecipeAdapter(this, mRecipeList);
        mRecipeAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        int list_recipe_column_item = getResources().getInteger(R.integer.list_recipe_column_item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, list_recipe_column_item);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mSwipeRefreshLayout.setRefreshing(false);
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

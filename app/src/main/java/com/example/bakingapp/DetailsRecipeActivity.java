package com.example.bakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.adapter.RecipeAdapterOnClick;
import com.example.bakingapp.fragment.RecipeDetailsListFragment;
import com.example.bakingapp.fragment.RecipeIngredientsListFragment;
import com.example.bakingapp.fragment.RecipeStepDetailsFragment;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utility.Constant;

public class DetailsRecipeActivity
        extends AppCompatActivity
        implements RecipeAdapterOnClick,
        RecipeStepDetailsFragment.RecipeStepClick {

    private Recipe mRecipe;
    private RecipeDetailsListFragment recipeDetailsListFragment;
    private RecipeIngredientsListFragment recipeIngredientsListFragment;
    private RecipeStepDetailsFragment recipeStepDetailsFragment;
    private int indexRecipe;
    private int layoutPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecipe = (Recipe) intent
                .getSerializableExtra(Constant.SELECTED_RECIPE_KEY);
        if (!mRecipe.getName().isEmpty()) {
            setTitle(mRecipe.getName());
        } else {
            setTitle(getString(R.string.details));
        }
        if (savedInstanceState != null) {
            layoutPosition = savedInstanceState.getInt(Constant.RECIPE_LAYOUT_POSITION_KEY, 0);
            indexRecipe = savedInstanceState.getInt(Constant.RECIPE_INDEX_SELECTED_KEY, 0);
        }
        showUI();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constant.RECIPE_LAYOUT_POSITION_KEY, layoutPosition);
        outState.putInt(Constant.RECIPE_INDEX_SELECTED_KEY, indexRecipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (layoutPosition == 0) {
            onBackPressed();
        } else {
            layoutPosition = 0;
            showUI();
        }
        return true;
    }

    @Override
    public void onClick(int position) {
        if (position == 0) {
            showRecipeIngredients();
        } else {
            indexRecipe = position - 1;
            showRecipeStep();
        }
    }

    @Override
    public void onNextClick() {
        indexRecipe++;
        checkButtonVisibility(recipeStepDetailsFragment);
        recipeStepDetailsFragment.setStep(mRecipe.getSteps().get(indexRecipe));
    }

    @Override
    public void onPreviousClick() {
        indexRecipe--;
        checkButtonVisibility(recipeStepDetailsFragment);
        recipeStepDetailsFragment.setStep(mRecipe.getSteps().get(indexRecipe));
    }

    private void showUI() {
        switch (layoutPosition) {
            case 0:
                showRecipe();
                break;
            case 1:
                showRecipeIngredients();
                break;
            case 2:
                showRecipeStep();
                break;
        }
    }

    private void showRecipe() {
        layoutPosition = 0;
        recipeDetailsListFragment = RecipeDetailsListFragment
                .newInstance(mRecipe);
        recipeDetailsListFragment.setRecipeAdapterOnClick(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_list_container, recipeDetailsListFragment)
                .commit();
    }

    private void showRecipeIngredients() {
        layoutPosition = 1;
        recipeIngredientsListFragment = RecipeIngredientsListFragment
                .newInstance(mRecipe.getIngredients());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_list_container, recipeIngredientsListFragment)
                .commit();
    }

    private void showRecipeStep() {
        layoutPosition = 2;
        recipeStepDetailsFragment = RecipeStepDetailsFragment
                .newInstance(mRecipe.getSteps().get(indexRecipe));
        checkButtonVisibility(recipeStepDetailsFragment);
        recipeStepDetailsFragment.setRecipeStepClick(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_list_container, recipeStepDetailsFragment)
                .commit();
    }

    private void checkButtonVisibility(RecipeStepDetailsFragment recipeStepDetailsFragment) {
        if (indexRecipe > 0) {
            recipeStepDetailsFragment.setVisiblePrevious(true);
        } else {
            recipeStepDetailsFragment.setVisiblePrevious(false);
        }
        if (indexRecipe < mRecipe.getSteps().size() - 2) {
            recipeStepDetailsFragment.setVisibleNext(true);
        } else {
            recipeStepDetailsFragment.setVisibleNext(false);
        }
    }
}
package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.adapter.RecipeAdapterOnClick;
import com.example.bakingapp.fragment.RecipeDetailsListFragment;
import com.example.bakingapp.fragment.RecipeIngredientsListFragment;
import com.example.bakingapp.fragment.RecipeStepDetailsFragment;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utility.Constant;

import java.io.Serializable;

public class DetailsRecipeActivity extends AppCompatActivity implements RecipeAdapterOnClick {

    private Recipe mRecipe;
    private int indexRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra(Constant.SELECTED_RECIPE_KEY);

        if (serializable != null) {
            try {
                mRecipe = (Recipe) serializable;
                if (!mRecipe.getName().isEmpty()) {
                    setTitle(mRecipe.getName());
                } else {
                    setTitle(getString(R.string.details));
                }

                showRecipe();
            } catch (Exception e) {
                //TODO show error
            }
        } else {
            //TODO show error
        }
    }

    private void showRecipe() {
        RecipeDetailsListFragment recipeDetailsListFragment = RecipeDetailsListFragment
                .newInstance(mRecipe);
        recipeDetailsListFragment.setRecipeAdapterOnClick(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_list_container, recipeDetailsListFragment)
                .commit();
    }

    @Override
    public void onClick(int position) {
        if (position == 0) {
            RecipeIngredientsListFragment recipeIngredientsListFragment = RecipeIngredientsListFragment
                    .newInstance(mRecipe.getIngredients());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_details_list_container, recipeIngredientsListFragment)
                    .commit();

        } else {
            indexRecipe = position - 1;
            final RecipeStepDetailsFragment recipeStepDetailsFragment = RecipeStepDetailsFragment
                    .newInstance(mRecipe.getSteps().get(indexRecipe));
            checkButtonVisibility(recipeStepDetailsFragment);
            recipeStepDetailsFragment.setRecipeStepClick(new RecipeStepDetailsFragment.RecipeStepClick() {
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
            });
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_details_list_container, recipeStepDetailsFragment)
                    .commit();
        }
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
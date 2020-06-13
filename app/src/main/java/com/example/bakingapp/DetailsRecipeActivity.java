package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utility.Constant;

import java.io.Serializable;

public class DetailsRecipeActivity extends AppCompatActivity {

    private Recipe mRecipe;

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
    }
}
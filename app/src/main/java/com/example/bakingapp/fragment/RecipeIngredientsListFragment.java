package com.example.bakingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.adapter.RecipeIngredientAdapter;
import com.example.bakingapp.adapter.RecipeStepAdapter;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.utility.Constant;


import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsListFragment extends Fragment {
    private List<Ingredient> mIngredient;
    private RecyclerView mRecyclerView;

    public static RecipeIngredientsListFragment newInstance(List<Ingredient> mIngredient) {
        RecipeIngredientsListFragment fragment = new RecipeIngredientsListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.RECIPE_INGREDIENTS_KEY, new ArrayList<>(mIngredient));
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setIngredient(List<Ingredient> mIngredient) {
        this.mIngredient = mIngredient;
        showListIngredient();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_ingredients_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_recipe_ingredients_list);

        mIngredient = (ArrayList) getArguments().getSerializable(Constant.RECIPE_INGREDIENTS_KEY);
        showListIngredient();
    }

    private void showListIngredient() {
        RecipeIngredientAdapter mRecipeAdapter = new RecipeIngredientAdapter(getContext(), (List<Ingredient>) mIngredient);
        mRecyclerView.setAdapter(mRecipeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}

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
import com.example.bakingapp.adapter.RecipeAdapterOnClick;
import com.example.bakingapp.adapter.RecipeStepAdapter;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.utility.Constant;

import java.util.List;

public class RecipeDetailsListFragment extends Fragment implements RecipeAdapterOnClick {
    private Recipe mRecipe;
    private RecyclerView mRecyclerView;
    private RecipeAdapterOnClick recipeAdapterOnClick;

    public static RecipeDetailsListFragment newInstance(Recipe mRecipe) {
        RecipeDetailsListFragment fragment = new RecipeDetailsListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.RECIPE_DETAILS_KEY, mRecipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setRecipe(Recipe mRecipe) {
        this.mRecipe = mRecipe;
        showListStep();
    }

    public void setRecipeAdapterOnClick(RecipeAdapterOnClick recipeAdapterOnClick) {
        this.recipeAdapterOnClick = recipeAdapterOnClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.recipe_details_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_recipe_details_list);

        mRecipe = (Recipe) getArguments().getSerializable(Constant.RECIPE_DETAILS_KEY);
        showListStep();
    }

    @Override
    public void onClick(int position) {
        if (recipeAdapterOnClick != null) {
            recipeAdapterOnClick.onClick(position);
        }
    }

    private void showListStep() {
        List<Step> stepList = mRecipe.getSteps();
        Step step = new Step();
        step.setShortDescription("Recipe Ingredients");
        stepList.add(0, step);
        RecipeStepAdapter mRecipeAdapter = new RecipeStepAdapter(getContext(), mRecipe.getSteps());
        mRecipeAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}

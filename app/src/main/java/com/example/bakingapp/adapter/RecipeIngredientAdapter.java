package com.example.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Ingredient;

import java.util.List;

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.RecipeIngredientAdapterHolder> {

    private Context context;
    private List<Ingredient> mIngredientList;

    public RecipeIngredientAdapter(Context context, List<Ingredient> mIngredientList) {
        this.context = context;
        this.mIngredientList = mIngredientList;
    }

    @NonNull
    @Override
    public RecipeIngredientAdapter.RecipeIngredientAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_ingredients_card, parent, false);
        return new RecipeIngredientAdapter.RecipeIngredientAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientAdapter.RecipeIngredientAdapterHolder holder, int position) {
        Ingredient ingredient = mIngredientList.get(position);
        holder.mTextViewIngredient.setText(ingredient.getIngredient());
        holder.mTextViewMeasure.setText(ingredient.getMeasure());
        holder.mTextViewQuantity.setText(String.valueOf(ingredient.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (mIngredientList != null) {
            return mIngredientList.size();
        } else {
            return 0;
        }
    }

    public static class RecipeIngredientAdapterHolder extends RecyclerView.ViewHolder {
        TextView mTextViewIngredient;
        TextView mTextViewQuantity;
        TextView mTextViewMeasure;

        public RecipeIngredientAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewIngredient = itemView.findViewById(R.id.tv_item_ingredient);
            mTextViewQuantity = itemView.findViewById(R.id.tv_item_quantity);
            mTextViewMeasure = itemView.findViewById(R.id.tv_item_measure);
        }
    }
}
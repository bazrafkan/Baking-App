package com.example.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private List<Recipe> mRecipeList;
    private static RecipeAdapterOnClick recipeAdapterOnClick;

    public RecipeAdapter(Context context, List<Recipe> mRecipeList) {
        this.context = context;
        this.mRecipeList = mRecipeList;
    }

    public void setOnClickListener(RecipeAdapterOnClick recipeAdapterOnClick) {
        this.recipeAdapterOnClick = recipeAdapterOnClick;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        String url = recipe.getImage();
        if (url.isEmpty()) {
            holder.mImageViewRecipe.setImageResource(R.drawable.recipe);
        } else {
            Picasso.get()
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.recipe)
                    .error(R.drawable.recipe)
                    .into(holder.mImageViewRecipe);
        }
        holder.mTextViewTitle.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipeList != null) {
            return mRecipeList.size();
        } else {
            return 0;
        }
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageViewRecipe;
        TextView mTextViewTitle;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewRecipe = itemView.findViewById(R.id.iv_item_photo);
            mTextViewTitle = itemView.findViewById(R.id.tv_item_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (recipeAdapterOnClick != null) {
                recipeAdapterOnClick.onClick(getAdapterPosition());
            }
        }
    }
}

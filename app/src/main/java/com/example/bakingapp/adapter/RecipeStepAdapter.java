package com.example.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Step;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterHolder> {

    private Context context;
    private List<Step> mStepList;
    private static RecipeAdapterOnClick recipeAdapterOnClick;

    public RecipeStepAdapter(Context context, List<Step> mStepList) {
        this.context = context;
        this.mStepList = mStepList;
    }

    public void setOnClickListener(RecipeAdapterOnClick recipeAdapterOnClick) {
        this.recipeAdapterOnClick = recipeAdapterOnClick;
    }

    @NonNull
    @Override
    public RecipeStepAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_details_card, parent, false);
        return new RecipeStepAdapter.RecipeStepAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterHolder holder, int position) {
        Step step = mStepList.get(position);
        holder.mTextViewTitle.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepList != null) {
            return mStepList.size();
        } else {
            return 0;
        }
    }

    public static class RecipeStepAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextViewTitle;

        public RecipeStepAdapterHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.tv_recipe_details_title);
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

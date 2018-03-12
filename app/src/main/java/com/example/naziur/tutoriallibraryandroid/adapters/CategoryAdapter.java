package com.example.naziur.tutoriallibraryandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.naziur.tutoriallibraryandroid.model.CategoryModel;
import com.example.naziur.tutoriallibraryandroid.R;

import java.util.ArrayList;

/**
 * Created by Hamidur on 11/03/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<CategoryModel> categoryData;
    private Context context;

    public CategoryAdapter (Context context) {
        this.context = context;
        loadCategoryData ();
    }

    private void loadCategoryData () {
        categoryData = new ArrayList<>();
        categoryData.add(new CategoryModel("A"));
        categoryData.add(new CategoryModel("Android", "1"));
        categoryData.add(new CategoryModel("A.I", "2"));
        categoryData.add(new CategoryModel("Anti-Matter", "3"));
        categoryData.add(new CategoryModel("Animals", "4"));
        categoryData.add(new CategoryModel("B"));
        categoryData.add(new CategoryModel("BobScript", "5"));
        categoryData.add(new CategoryModel("Building", "6"));
        categoryData.add(new CategoryModel("D"));
        categoryData.add(new CategoryModel("Data", "7"));
        categoryData.add(new CategoryModel("Data Mining", "8"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0 :
                View categoryGroupingView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_category_grouping, parent, false);
                return new CategoryGroupHolder(categoryGroupingView);

            case 1 :
                View categoryItemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_category_name, parent, false);
                return new CategoryItemHolder(categoryItemView);

            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0 :
                ((CategoryGroupHolder) holder).bind(context, categoryData.get(position));
                break;

            case 1 :
                ((CategoryItemHolder) holder).bind(context, categoryData.get(position));
                break;

            default: break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        CategoryModel categoryModel = categoryData.get(position);
        return categoryModel.getType();
    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    private static class CategoryGroupHolder extends RecyclerView.ViewHolder {
        private TextView categoryLetter;

        public CategoryGroupHolder(View itemView) {
            super(itemView);
            categoryLetter = (TextView) itemView.findViewById(R.id.categoryLetter);
        }

        void bind (Context context, CategoryModel categoryModel) {
            categoryLetter.setText(categoryModel.getText());
        }
    }

    private static class CategoryItemHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;

        public CategoryItemHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
        }

        void bind (Context context, CategoryModel categoryModel) {
            categoryName.setText(categoryModel.getText());
        }
    }
}
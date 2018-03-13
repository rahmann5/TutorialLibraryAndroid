package com.example.naziur.tutoriallibraryandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.naziur.tutoriallibraryandroid.R;

/**
 * Created by Hamidur on 13/03/2018.
 */

public class ReferenceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private String[] references;
    private Context context;

    public ReferenceAdapter (Context context, String[] references) {
        this.references = references;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View referenceHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_name, parent, false);
        return new ReferencesHolder(referenceHolder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ReferencesHolder) holder).bind(context, references[position]);
    }

    @Override
    public int getItemCount() {
        return references.length;
    }

    private static class ReferencesHolder extends RecyclerView.ViewHolder {

        private TextView reference;

        public ReferencesHolder(View itemView) {
            super(itemView);
            reference = (TextView) itemView.findViewById(R.id.categoryName);
        }

        void bind (Context context, String ref) {
            reference.setText(ref);
        }
    }
}

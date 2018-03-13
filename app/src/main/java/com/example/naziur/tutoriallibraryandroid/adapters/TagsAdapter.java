package com.example.naziur.tutoriallibraryandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.model.TagModel;

/**
 * Created by Hamidur on 12/03/2018.
 */

public class TagsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    TagModel [] tags;
    private Context context;

    public TagsAdapter (Context context, TagModel [] tags) {
        this.tags = tags;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tagView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tag, parent, false);
        return new TagHolder(tagView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TagHolder) holder).bind(context, tags[position]);
    }

    @Override
    public int getItemCount() {
        return tags.length;
    }

    private static class TagHolder extends RecyclerView.ViewHolder {

        private TextView tagName;

        public TagHolder(View itemView) {
            super(itemView);
            tagName = (TextView) itemView.findViewById(R.id.tagName);

        }

        void bind (Context context, TagModel tag) {
            tagName.setText(tag.getTagName());
        }
    }
}

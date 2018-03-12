package com.example.naziur.tutoriallibraryandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.model.CategoryModel;
import com.example.naziur.tutoriallibraryandroid.model.SectionModel;

/**
 * Created by Hamidur on 12/03/2018.
 */

public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SectionModel[] sections;
    private Context context;

    public SectionAdapter (Context context, SectionModel[] tutorialSections) {
        sections = tutorialSections;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View sectionItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tutorial_section, parent, false);
        return new SectionHolder(sectionItemView );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SectionHolder) holder).bind(context, sections[position]);
    }

    @Override
    public int getItemCount() {
        return sections.length;
    }

    private static class SectionHolder extends RecyclerView.ViewHolder {

        private TextView sectionHeading, sectionDetail;
        private ImageView sectionImg;

        public SectionHolder(View itemView) {
            super(itemView);
            sectionHeading = (TextView) itemView.findViewById(R.id.section_heading);
            sectionDetail = (TextView) itemView.findViewById(R.id.section_detail);
            sectionImg = (ImageView) itemView.findViewById(R.id.section_img);
        }

        void bind (Context context, SectionModel sectionModel) {
            sectionHeading.setText(sectionModel.getHeading());
            sectionDetail.setText(sectionModel.getDetail());
            //sectionImg.setText(sectionModel.getImage());
        }
    }
}

package com.example.naziur.tutoriallibraryandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naziur on 11/03/2018.
 */

public class TutorialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TutorialModel> tutorialModels;

    private Context mContext;


    public TutorialAdapter(Context context) {
        mContext = context;
        tutorialModels = new ArrayList<>();
    }

    public void setTutorialModels(List<TutorialModel> tutorialModels) {
        this.tutorialModels = tutorialModels;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorials_list_item, parent, false);
        return new TutorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TutorialViewHolder) holder).bind(
                mContext,
                tutorialModels.get(position));
    }

    @Override
    public int getItemCount() {
        return tutorialModels.size();
    }

    public static class TutorialViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTv, introTv, createdTv, tagOneTv, tagTwoTv;
        public ImageView introIv;

        public TutorialViewHolder(View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.title_tv);
            introTv = (TextView) view.findViewById(R.id.intro_tv);
            createdTv = (TextView) view.findViewById(R.id.created_tv);
            tagOneTv = (TextView) view.findViewById(R.id.tag_one_tv);
            tagTwoTv = (TextView) view.findViewById(R.id.tag_two_tv);
            introIv = (ImageView) view.findViewById(R.id.intro_image);
        }

        private void bind(Context context, final TutorialModel tutorialModel) {
            titleTv.setText(tutorialModel.getTitle());
            introTv.setText(tutorialModel.getIntro());
            createdTv.setText(tutorialModel.getCreatedAtDate());
            Glide.with(context).load(tutorialModel.getIntroImageUrl()).into(introIv);
            tagOneTv.setText(tutorialModel.getTags()[0].getTagName());
            tagOneTv.setVisibility(View.VISIBLE);
            if(tutorialModel.getTags().length > 1){
                tagTwoTv.setText(tutorialModel.getTags()[1].getTagName());
                tagTwoTv.setVisibility(View.VISIBLE);
            }
        }

    }
}

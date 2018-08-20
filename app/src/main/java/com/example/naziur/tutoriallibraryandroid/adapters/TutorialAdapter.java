package com.example.naziur.tutoriallibraryandroid.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public interface ViewClickListener {

        void onViewClick(boolean isTutorial, String id);
    }

    public static ViewClickListener listener;

    public TutorialAdapter(Context context, ViewClickListener viewClickListener) {
        mContext = context;
        tutorialModels = new ArrayList<>();
        listener = viewClickListener;
    }

    public void setTutorialModels(List<TutorialModel> tutorialModels) {
        this.tutorialModels = tutorialModels;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tutorials, parent, false);
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

    public void clear() {
        final int size = tutorialModels.size();
        tutorialModels.clear();
        notifyItemRangeRemoved(0, size);
    }

    public static class TutorialViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTv, createdTv, tagOneTv, tagTwoTv;
        public Button viewTutBtn;
        public WebView introWv;
        public ImageView introIv;
        public LinearLayout linearLayout;

        public TutorialViewHolder(View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.title_tv);
            introWv = (WebView) view.findViewById(R.id.intro_wv);
            createdTv = (TextView) view.findViewById(R.id.created_tv);
            introIv = (ImageView) view.findViewById(R.id.intro_image);
            viewTutBtn = (Button) view.findViewById(R.id.view_tut_btn);
            linearLayout = (LinearLayout) view.findViewById(R.id.tag_container);
        }

        private void bind(Context context, final TutorialModel tutorialModel) {
            titleTv.setText(tutorialModel.getTitle());
            introWv.loadData(tutorialModel.getIntro(), "text/html; charset=utf-8", "UTF-8");
            createdTv.setText(tutorialModel.getCreatedAtDate());
            Glide.with(context).load(tutorialModel.getIntroImageUrl()).into(introIv);
            final TextView[] tv = new TextView[tutorialModel.getTags().length];
            linearLayout.removeAllViewsInLayout();
            for(int i = 0; i < tutorialModel.getTags().length; i++){
                tv[i] = new TextView(context);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 10, 0);
                tv[i].setLayoutParams(params);
                tv[i].setText(tutorialModel.getTags()[i].getTagName());
                tv[i].setTextColor(ContextCompat.getColor(context, R.color.tags_text_colour));
                tv[i].setBackgroundColor(ContextCompat.getColor(context, R.color.tags_bg));
                tv[i].setPadding(5, 5, 5, 5);
                final int tag_id = i;
                tv[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onViewClick(false, ""+tutorialModel.getTags()[tag_id].getTagId());
                    }
                });

                linearLayout.addView(tv[i]);
            }

            viewTutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onViewClick(true, tutorialModel.getId());
                }
            });
        }

    }
}

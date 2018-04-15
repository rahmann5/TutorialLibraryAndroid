package com.example.naziur.tutoriallibraryandroid.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naziur.tutoriallibraryandroid.MainActivity;
import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.fragment.MainFragment;
import com.example.naziur.tutoriallibraryandroid.fragment.TutorialViewerFragment;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.utility.Constants;

/**
 * Created by Hamidur on 09/04/2018.
 */

import java.util.ArrayList;
import java.util.List;

import static com.example.naziur.tutoriallibraryandroid.database.FavoriteTutorialContract.FavoriteTutorialEntry;

public class SavedTutorialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    interface OnClickListener {
        void onClick (View view, int position);
    }

    private Context context;
    public List<TutorialModel> savedTuts;

    public SavedTutorialAdapter (Context context, Cursor c) {
        this.context = context;
        readCursorData(c);
    }

    @Override
    public SavedTutorialViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_saved_tutorial, parent, false);
        return new SavedTutorialViewHolder(v);
    }

    private void readCursorData (Cursor c) {
        savedTuts = new ArrayList<>(); // do not place a fixed size
        try {
            while (c.moveToNext()) {
                savedTuts.add(new TutorialModel(
                    c.getString(c.getColumnIndex(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_ID)),
                    c.getString(c.getColumnIndex(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_TITLE)),
                    c.getString(c.getColumnIndex(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_AUTHOR))
                ));
            }
        } finally {
            c.close();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SavedTutorialViewHolder ) holder).bind(
                savedTuts.get(position).getTitle(),
                savedTuts.get(position).getAuthor()
        );

        ((SavedTutorialViewHolder ) holder).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FRAGMENT_KEY_TUT_ID, savedTuts.get(position).getId());
                MainFragment fragment = TutorialViewerFragment.newInstance();
                fragment.setArguments(bundle);
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedTuts.size();
    }

    public void updateState (int pos) {
        savedTuts.remove(pos);
        notifyItemRemoved(pos);
    }

    private static class SavedTutorialViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView title, author;
        private SavedTutorialAdapter.OnClickListener onClickListener;

        public SavedTutorialViewHolder  (View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tutorial_name);
            author = (TextView) itemView.findViewById(R.id.tutorial_author);
            itemView.setOnClickListener(this);
        }

        public void setOnClickListener (SavedTutorialAdapter.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick (View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }

        void bind (String tTile, String tAuthor) {
            title.setText(tTile);
            author.setText(tAuthor);
        }

    }

}



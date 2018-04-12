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
import com.example.naziur.tutoriallibraryandroid.utility.Constants;
import com.example.naziur.tutoriallibraryandroid.utility.CursorRecyclerAdapter;

/**
 * Created by Hamidur on 09/04/2018.
 */

import static com.example.naziur.tutoriallibraryandroid.database.FavoriteTutorialContract.FavoriteTutorialEntry;

public class SavedTutorialAdapter extends CursorRecyclerAdapter<SimpleViewHolder> {

    interface OnClickListener {
        void onClick (View view, int position);
    }

    private Context context;

    public SavedTutorialAdapter (Context context, Cursor c) {
        super(c);
        this.context = context;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_saved_tutorial, parent, false);
        return new SimpleViewHolder(v);
    }

    @Override
    public void onBindViewHolder (SimpleViewHolder holder, final Cursor cursor) {
        holder.bind(
            cursor.getString(cursor.getColumnIndex(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_TITLE)),
            cursor.getString(cursor.getColumnIndex(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_AUTHOR)),
            cursor.getString(cursor.getColumnIndex(FavoriteTutorialEntry.COLUMN_NAME_TUTORIAL_ID))
            );

        holder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                String tid = (String) view.findViewById(R.id.tutorial_name).getTag();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FRAGMENT_KEY_TUT_ID, tid);
                MainFragment fragment = TutorialViewerFragment.newInstance();
                fragment.setArguments(bundle);
                ((MainActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        return super.swapCursor(c);
    }
}

class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView title, author;
    private SavedTutorialAdapter.OnClickListener onClickListener;

    public SimpleViewHolder (View itemView)
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

    void bind (String tTile, String tAuthor, String tid) {
        title.setText(tTile);
        title.setTag(tid);
        author.setText(tAuthor);
    }

}

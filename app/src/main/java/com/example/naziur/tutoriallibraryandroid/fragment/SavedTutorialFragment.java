package com.example.naziur.tutoriallibraryandroid.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naziur.tutoriallibraryandroid.MainActivity;
import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.adapters.SavedTutorialAdapter;
import com.example.naziur.tutoriallibraryandroid.database.TutorialDBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedTutorialFragment extends MainFragment {

    private TutorialDBHelper tutorialDb;
    private RecyclerView savedTutorialRecyclerView;
    private SavedTutorialAdapter savedTutorialAdapter;
    private int position;

    public SavedTutorialFragment() {
        // Required empty public constructor
    }

    public static SavedTutorialFragment newInstance() {
        SavedTutorialFragment savedTutorialFragment = new SavedTutorialFragment();
        return savedTutorialFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setComponentVisibleListener();
        componentVisibleListener.resetLayout();
        View view = inflater.inflate(R.layout.fragment_tutorials, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_saved_tut));
        tutorialDb = new TutorialDBHelper(getContext());
        savedTutorialRecyclerView = (RecyclerView) view.findViewById(R.id.all_recycle_view);
        setUpRecycler ();
        return view;
    }

    private void setUpRecycler () {

        Cursor c = tutorialDb.getAllMyTutorial();
        if ((c != null) && (c.getCount() > 0)) {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            savedTutorialAdapter = new SavedTutorialAdapter(getActivity(),c);
            savedTutorialRecyclerView.setLayoutManager(mLayoutManager);
            savedTutorialRecyclerView.setAdapter(savedTutorialAdapter);

            final Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.undo_text, Snackbar.LENGTH_LONG);


            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    if (!snack.isShown()) {
                        snack.show();
                        position = viewHolder.getAdapterPosition();
                    } else {
                        snack.dismiss();
                        removeItemFromDb ();
                        position = viewHolder.getAdapterPosition();
                        snack.show();
                    }
                }
            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(savedTutorialRecyclerView);

            snack.addCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        removeItemFromDb ();
                    }
                }

                @Override
                public void onShown(Snackbar snackbar) {

                }
            });

            snack.setAction("UNDO", new UndoListener(snack, savedTutorialAdapter));

            componentVisibleListener.onErrorFound(false, "");
        } else {
            componentVisibleListener.onErrorFound(true, "No saved tutorials");
        }

    }

    private void removeItemFromDb () {
        String tid = savedTutorialAdapter.savedTuts.get(position).getId();
        tutorialDb.removeFavTutorial(tid);
        savedTutorialAdapter.updateState(position);
        if (savedTutorialAdapter.getItemCount() == 0) {
            componentVisibleListener.onErrorFound(true, "No saved tutorials");
        }
    }

    @Override
    public void onDestroy() {
        tutorialDb.close();
        super.onDestroy();
    }

    private class UndoListener implements View.OnClickListener{

        Snackbar bar;
        SavedTutorialAdapter adapter;

        public UndoListener (Snackbar bar, SavedTutorialAdapter adapter) {
            this.bar = bar;
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {
            bar.dismiss();
            this.adapter.notifyDataSetChanged();
        }
    }

}

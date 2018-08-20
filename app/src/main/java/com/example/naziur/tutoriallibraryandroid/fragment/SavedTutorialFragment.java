package com.example.naziur.tutoriallibraryandroid.fragment;


import android.os.Bundle;
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
        View view = inflater.inflate(R.layout.fragment_tutorials, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_saved_tut));
        tutorialDb = new TutorialDBHelper(getContext());
        savedTutorialRecyclerView = (RecyclerView) view.findViewById(R.id.all_recycle_view);
        setUpRecycler ();
        return view;
    }

    private void setUpRecycler () {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        savedTutorialAdapter = new SavedTutorialAdapter(getActivity(),tutorialDb.getAllMyTutorial());
        savedTutorialRecyclerView.setLayoutManager(mLayoutManager);
        savedTutorialRecyclerView.setAdapter(savedTutorialAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                String tid = savedTutorialAdapter.savedTuts.get(position).getId();
                tutorialDb.removeFavTutorial(tid);
                savedTutorialAdapter.updateState(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(savedTutorialRecyclerView);

    }

    @Override
    public void onDestroy() {
        tutorialDb.close();
        super.onDestroy();
    }

}

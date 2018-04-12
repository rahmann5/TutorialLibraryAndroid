package com.example.naziur.tutoriallibraryandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.adapters.SavedTutorialAdapter;
import com.example.naziur.tutoriallibraryandroid.adapters.SectionAdapter;
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
        View view = inflater.inflate(R.layout.fragment_saved_tutorial, container, false);
        tutorialDb = new TutorialDBHelper(getContext());
        savedTutorialRecyclerView = (RecyclerView) view.findViewById(R.id.saved_tutorial_list);
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
                String tid = (String) viewHolder.itemView.findViewById(R.id.tutorial_name).getTag();
                tutorialDb.removeFavTutorial(tid);
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

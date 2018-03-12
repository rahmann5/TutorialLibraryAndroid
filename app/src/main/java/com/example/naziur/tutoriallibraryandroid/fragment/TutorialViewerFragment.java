package com.example.naziur.tutoriallibraryandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.adapters.SectionAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialViewerFragment extends MainFragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SectionAdapter sectionAdapter;
    private TutorialModel tutorialModel;

    public TutorialViewerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial_viewer, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutorial_section_viewer);
        setUpRecyclerView();
        loadData ();
        return view;
    }

    private void setUpRecyclerView () {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    private void loadData () {
        // get the extra bundle called the tutorial id
        // need to use tutorial id to get json file from server
        // on success load data

        //tutorialModel = new TutorialModel();


    }

}

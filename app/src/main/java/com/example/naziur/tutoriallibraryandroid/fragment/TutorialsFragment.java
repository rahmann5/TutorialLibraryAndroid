package com.example.naziur.tutoriallibraryandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.adapters.TutorialAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialsFragment extends MainFragment {

    private TutorialAdapter tutorialAdapter;
    private LinearLayoutManager mLayoutManager;

    public static TutorialsFragment newInstance() {
        TutorialsFragment tutorialsFragment = new TutorialsFragment();
        return tutorialsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tutorials, container, false);
        tutorialAdapter = new TutorialAdapter(getContext());
        RecyclerView mRecyclerView = rootView.findViewById(R.id.tutorials_recycle_view);

        List<TutorialModel> data = new ArrayList<>();
        data.add(new TutorialModel("A test title", "Mr Author", "A short intro for test", "http://tutoriallibrary.000webhostapp.com/assets/images/tutorials/10/intro-image.jpg", "08/12/1874", new String[]{"Lifestyle"}));
        data.add(new TutorialModel("A test title2", "Mr Author", "Another short intro for test", "http://tutoriallibrary.000webhostapp.com/assets/images/tutorials/17/intro-image.jpg", "08/01/1874", new String[]{"Lifestyle", "Android"}));

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(tutorialAdapter);
        tutorialAdapter.setTutorialModels(data);
        return rootView;
    }

}

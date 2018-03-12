package com.example.naziur.tutoriallibraryandroid.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.adapters.CategoryAdapter;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends MainFragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CategoryAdapter categoryAdapter;
    private TextView emptyActivity;
    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        emptyActivity = (TextView) view.findViewById(R.id.empty_category);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_category_viewer);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView () {
        categoryAdapter = new CategoryAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(categoryAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        toggleEmptyActivityIcon();
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    private void toggleEmptyActivityIcon(){
        if(categoryAdapter.getItemCount() > 0) {
            emptyActivity.setVisibility(View.GONE);
        } else {
            emptyActivity.setVisibility(View.VISIBLE);
        }
    }
}
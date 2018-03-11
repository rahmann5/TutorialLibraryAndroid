package com.example.naziur.tutoriallibraryandroid.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naziur.tutoriallibraryandroid.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends MainFragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}

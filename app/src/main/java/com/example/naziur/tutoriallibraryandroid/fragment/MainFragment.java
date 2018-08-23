package com.example.naziur.tutoriallibraryandroid.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.naziur.tutoriallibraryandroid.MainActivity;
import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.utility.ProgressDialog;
import com.example.naziur.tutoriallibraryandroid.utility.Constants;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Hamidur on 11/03/2018.
 */

public class MainFragment extends Fragment implements ScreenShotable {

    protected ProgressDialog progressDialog;

    public interface OnComponentVisibleListener {
        void onErrorFound(boolean error, String errorMsg);
        void resetLayout ();
    }

    protected OnComponentVisibleListener componentVisibleListener;

    public MainFragment() {
        // Required empty public constructor

    }

    public static MainFragment newInstance(){
        MainFragment mainFragment = new MainFragment();

        return mainFragment;
    }

    public void setComponentVisibleListener () {
        componentVisibleListener = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        return v;
    }

    protected void setActionBarTitle (String title) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }


}

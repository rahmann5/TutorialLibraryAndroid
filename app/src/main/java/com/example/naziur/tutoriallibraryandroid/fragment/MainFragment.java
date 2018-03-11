package com.example.naziur.tutoriallibraryandroid.fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Hamidur on 11/03/2018.
 */

public class MainFragment extends Fragment implements ScreenShotable {

    public static MainFragment newInstance(){
        return null;
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}

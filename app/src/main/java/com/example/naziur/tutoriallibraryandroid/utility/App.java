package com.example.naziur.tutoriallibraryandroid.utility;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.bumptech.glide.request.target.ViewTarget;
import com.example.naziur.tutoriallibraryandroid.R;

/**
 * Created by Hamidur on 08/04/2018.
 */

public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}

package com.example.naziur.tutoriallibraryandroid.utility;

import android.app.Activity;
import android.content.Intent;

import com.example.naziur.tutoriallibraryandroid.R;

/**
 * Created by Naziur on 25/08/2018.
 */

public class ThemeManager {

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_MATERIAL_LIGHT = 1;
    public final static int THEME_MATERIAL_DARK = 2;

    public static void changeToTheme(Activity activity) {
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public static void onActivityCreateSetTheme(Activity activity, int sTheme) {
        switch (sTheme) {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_MATERIAL_LIGHT:
                activity.setTheme(R.style.Theme_Default_Light);
                break;
            case THEME_MATERIAL_DARK:
                activity.setTheme(R.style.Theme_Default_Dark);
                break;
        }
    }


}

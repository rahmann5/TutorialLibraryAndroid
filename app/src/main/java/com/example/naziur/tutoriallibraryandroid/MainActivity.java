package com.example.naziur.tutoriallibraryandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.example.naziur.tutoriallibraryandroid.fragment.CategoryFragment;
import com.example.naziur.tutoriallibraryandroid.fragment.HomeFragment;
import com.example.naziur.tutoriallibraryandroid.fragment.MainFragment;
import com.example.naziur.tutoriallibraryandroid.fragment.TutorialViewerFragment;
import com.example.naziur.tutoriallibraryandroid.fragment.SearchFragment;
import com.example.naziur.tutoriallibraryandroid.fragment.TutorialsFragment;
import com.example.naziur.tutoriallibraryandroid.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private MainFragment mainFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mainFragment)
                .addToBackStack(null)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, mainFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(Constants.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(Constants.HOME, R.drawable.ic_home);
        list.add(menuItem);
        SlideMenuItem menuItem1 = new SlideMenuItem(Constants.TUTORIAL, R.drawable.ic_tutorial);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem(Constants.CATEGORY, R.drawable.ic_category);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(Constants.MY_TUTORIALS, R.drawable.ic_my_tutorials);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(Constants.RANDOM, R.drawable.ic_random_tut);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(Constants.ABOUT, R.drawable.ic_about);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(Constants.SEARCH, R.drawable.ic_search);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(Constants.SETTING, R.drawable.ic_settings);
        list.add(menuItem7);
        SlideMenuItem menuItem8 = new SlideMenuItem(Constants.FEEDBACK, R.drawable.ic_feedback);
        list.add(menuItem8);
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, String selected ,int topPosition) {

        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        //findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        MainFragment mainFragment = getSelectedFragment(selected);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).addToBackStack(null).commit();
        return mainFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case Constants.CLOSE:
                return screenShotable;
            default:
                return replaceFragment(screenShotable, slideMenuItem.getName() ,position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    private MainFragment getSelectedFragment (String itemName) {
        switch (itemName) {
            case Constants.CATEGORY :
                return CategoryFragment.newInstance();
            case Constants.HOME:
                return MainFragment.newInstance();
            case Constants.TUTORIAL:
                return TutorialsFragment.newInstance();
            case Constants.RANDOM:
                MainFragment fragment = TutorialViewerFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FRAGMENT_KEY_TUT_ID, Constants.RANDOM);
                fragment.setArguments(bundle);
                return fragment;
            case Constants.SEARCH:
                return SearchFragment.newInstance();
            default:
                return HomeFragment.newInstance();
        }
    }
}


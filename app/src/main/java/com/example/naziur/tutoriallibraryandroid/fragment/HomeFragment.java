package com.example.naziur.tutoriallibraryandroid.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.naziur.tutoriallibraryandroid.MainActivity;
import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.utility.Constants;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class HomeFragment extends MainFragment implements View.OnClickListener {


    private View containerView;
    private LinearLayout tutorialBtn, categoryBtn, myTutorialBtn, randomBtn, aboutBtn, searchBtn, settingBtn, feedbackBtn;

    private Bitmap bitmap;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setComponentVisibleListener();
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_home));

        tutorialBtn = (LinearLayout) v.findViewById(R.id.tutorial);
        categoryBtn = (LinearLayout) v.findViewById(R.id.category);
        myTutorialBtn = (LinearLayout) v.findViewById(R.id.my_tutorials);
        randomBtn = (LinearLayout) v.findViewById(R.id.random);
        aboutBtn = (LinearLayout) v.findViewById(R.id.about);
        searchBtn = (LinearLayout) v.findViewById(R.id.search);
        settingBtn = (LinearLayout) v.findViewById(R.id.settings);
        feedbackBtn = (LinearLayout) v.findViewById(R.id.feedack);

        tutorialBtn.setOnClickListener(this);
        categoryBtn.setOnClickListener(this);
        myTutorialBtn.setOnClickListener(this);
        randomBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);

        componentVisibleListener.onErrorFound(false, "");

        return v;
    }

    @Override
    public void onClick(View view) {
        MainFragment mainFragment = HomeFragment.newInstance();
        switch (view.getId()) {
            case R.id.tutorial :
                mainFragment = TutorialsFragment.newInstance();
                break;

            case R.id.category :
                mainFragment = CategoryFragment.newInstance();
                break;

            case R.id.my_tutorials :
                mainFragment = SavedTutorialFragment.newInstance();
                break;

            case R.id.random :
                mainFragment = TutorialViewerFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FRAGMENT_KEY_TUT_ID, Constants.RANDOM);
                mainFragment.setArguments(bundle);
                break;

            case R.id.about :
                mainFragment = AboutFragment.newInstance();
                break;

            case R.id.search :
                mainFragment = SearchFragment.newInstance();
                break;

            case R.id.settings :
                mainFragment = SettingsFragment.newInstance();
                break;

            case R.id.feedack :
                mainFragment = FeedbackFragment.newInstance();
                break;
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mainFragment).addToBackStack(null).commit();
    }

}


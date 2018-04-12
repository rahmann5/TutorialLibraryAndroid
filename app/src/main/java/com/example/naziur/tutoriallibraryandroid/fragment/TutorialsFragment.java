package com.example.naziur.tutoriallibraryandroid.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.model.TagModel;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.adapters.TutorialAdapter;
import com.example.naziur.tutoriallibraryandroid.utility.Constants;
import com.example.naziur.tutoriallibraryandroid.utility.ServerRequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.naziur.tutoriallibraryandroid.utility.Constants.FRAGMENT_KEY_TUT_ID;

/**
 * A simple {@link Fragment} subclass.
 *
 * [[{"tid":"10","slug":"Java-Brogramming-beginner","author_id":"1","title":"Java Brogramming beginner","img":"tutorials\/10\/intro-image.jpg","intro":"<p>Learn the basics of Java<\/p>","created":"2018-02-18 19:21:44","aid":"1","name":"test","email":"test@gmail.com","password":"c4ca4238a0b923820dcc509a6f75849b","registeration_date":"2018-01-28 13:19:09"},{"tid":"16","slug":"Making-your-very-own-Butorial","author_id":"1","title":"Making your very own Butorial","img":"tutorials\/16\/intro-image.jpg","intro":"<p>A butorial is a tutorial but involves a brute wyvern and a mangoose<\/p>","created":"2018-03-03 11:23:07","aid":"1","name":"test","email":"test@gmail.com","password":"c4ca4238a0b923820dcc509a6f75849b","registeration_date":"2018-01-28 13:19:09"},{"tid":"17","slug":"How-to-educate-yourself","author_id":"1","title":"How to educate yourself","img":"tutorials\/17\/intro-image.jpg","intro":"<p>This is a tutorial on how to educate youself.<\/p>","created":"2018-03-03 13:36:34","aid":"1","name":"test","email":"test@gmail.com","password":"c4ca4238a0b923820dcc509a6f75849b","registeration_date":"2018-01-28 13:19:09"},{"tid":"18","slug":"How-to-use-a-calculator","author_id":"1","title":"How to use a calculator","img":"tutorials\/18\/intro-image.png","intro":"<p>In this tutorial I will teach you how to use these <strong>Four<\/strong> features of a calculator:<\/p><ol><li>Addition<\/li><li>Subtraction<\/li><li>Multiplication<\/li><li>Division<\/li><\/ol>","created":"2018-04-05 11:25:25","aid":"1","name":"test","email":"test@gmail.com","password":"c4ca4238a0b923820dcc509a6f75849b","registeration_date":"2018-01-28 13:19:09"}],{"10":[["Java","2"],["Life Style","7"]],"16":[["Android","1"],["Java","2"],["Testing","8"]],"17":[["Life Style","7"]],"18":[["Life Style","7"]]}]
 *
 */
public class TutorialsFragment extends MainFragment implements ServerRequestManager.OnRequestCompleteListener{

    private TutorialAdapter tutorialAdapter;
    private LinearLayoutManager mLayoutManager;
    private String tagId;

    public static TutorialsFragment newInstance() {
        TutorialsFragment tutorialsFragment = new TutorialsFragment();
        return tutorialsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tutorials, container, false);
        tutorialAdapter = new TutorialAdapter(getContext(), new TutorialAdapter.ViewClickListener() {
            @Override
            public void onViewClick(boolean isTutorial, String id) {
                // do what you want with view
                if(isTutorial) {
                    Bundle args = new Bundle();
                    args.putString(FRAGMENT_KEY_TUT_ID, id);
                    Fragment fragment = new TutorialViewerFragment();
                    switchFragment(fragment, args);
                } else {
                    tagId = id;
                    Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    if (currentFragment instanceof TutorialsFragment) {
                        FragmentTransaction fragTransaction =   (getActivity()).getSupportFragmentManager().beginTransaction();
                        fragTransaction.detach(currentFragment);
                        fragTransaction.attach(currentFragment);
                        fragTransaction.commit();
                        System.out.println("Tag id sent is "+ tagId);
                    }
                }
            }
        });
        RecyclerView mRecyclerView = rootView.findViewById(R.id.tutorials_recycle_view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(tutorialAdapter);

        ServerRequestManager.setOnRequestCompleteListener(this);

        if(savedInstanceState != null) {
            tagId = savedInstanceState.getString(Constants.FRAGMENT_KEY_TAG_ID);
        }

        if(tagId == null)
            ServerRequestManager.getTutorials(getActivity().getApplicationContext());
        else
            ServerRequestManager.getAllTutorialsForTag(getActivity().getApplicationContext(), tagId);

        return rootView;
    }

    @Override
    public void onSuccessfulRequestListener(String command, String... s) {
        switch(command){
            case ServerRequestManager.COMMAND_GET_ALL_TUTORIALS:
            case ServerRequestManager.COMMAND_TUTORIALS_FOR_TAG:
                try {
                    //System.out.println(s[0]);
                    JSONArray array = new JSONArray(s[0]);
                    List<TutorialModel> data = new ArrayList<>();
                    JSONArray jsonArray = array.getJSONArray(0);
                    TutorialModel tutorialModel;

                    JSONObject tagsObject = array.getJSONObject(1);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tutorialObject = jsonArray.getJSONObject(i);
                        JSONArray tagsForOneTutorial = tagsObject.getJSONArray(tutorialObject.getString("tid"));
                        TagModel[] tags= new TagModel[tagsForOneTutorial.length()];
                        for(int j = 0; j < tagsForOneTutorial.length(); j++){
                            JSONArray singleTagInfo = tagsForOneTutorial.getJSONArray(j);
                            tags[j]= new TagModel(singleTagInfo.getString(0), singleTagInfo.getString(1));
                        }


                        tutorialModel = new TutorialModel(
                                tutorialObject.getString("tid"),
                                tutorialObject.getString("title"),
                                tutorialObject.getString("name"),
                                tutorialObject.getString("intro"),
                                "http://tutoriallibrary.000webhostapp.com/assets/images/"+tutorialObject.getString("img"),
                                convertFormat(tutorialObject.getString("created")),
                                tags
                        );
                        data.add(tutorialModel);
                    }
                    tutorialAdapter.setTutorialModels(data);
                    tutorialAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    System.out.println("JSONException ERROR " + e.getMessage());
                    e.printStackTrace();
                }
        }
    }

    private void switchFragment(Fragment fragment, Bundle args){
        if(args != null)
            fragment.setArguments(args);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public String convertFormat(String inputDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date = null;
        try {
            date = simpleDateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (date == null) {
            return "";
        }

        SimpleDateFormat convetDateFormat = new SimpleDateFormat("dd MMM yyyy");

        return convetDateFormat.format(date);
    }

    @Override
    public void onFailedRequestListener(String command, String... s) {

    }
}

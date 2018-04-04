package com.example.naziur.tutoriallibraryandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.model.TagModel;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.adapters.TutorialAdapter;
import com.example.naziur.tutoriallibraryandroid.utility.ServerRequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialsFragment extends MainFragment implements ServerRequestManager.OnRequestCompleteListener{

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

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(tutorialAdapter);

        ServerRequestManager.setOnRequestCompleteListener(this);
        ServerRequestManager.getTutorials(getActivity().getApplicationContext());
        return rootView;
    }

    @Override
    public void onSuccessfulRequestListener(String command, String... s) {
        switch(command){
            case ServerRequestManager.COMMAND_GET_ALL_TUTORIALS:
                try {
                    System.out.println(s[0]);
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

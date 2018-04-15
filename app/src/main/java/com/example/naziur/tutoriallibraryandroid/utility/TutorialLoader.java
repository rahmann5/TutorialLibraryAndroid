package com.example.naziur.tutoriallibraryandroid.utility;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.naziur.tutoriallibraryandroid.model.TagModel;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Naziur on 12/04/2018.
 */

public class TutorialLoader extends AsyncTaskLoader<List<TutorialModel>> {

    private List<TutorialModel> tutorialModels;
    private String json;
    public TutorialLoader(Context context, String json){
        super(context);
        this.json = json;
        tutorialModels = new ArrayList<>();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }



    @Override
    public List<TutorialModel> loadInBackground() {

        try {
            //System.out.println(json);
            JSONArray array = new JSONArray(json);
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
                tutorialModels.add(tutorialModel);
            }

        } catch (JSONException e) {
            System.out.println("JSONException ERROR " + e.getMessage());
            e.printStackTrace();
        }

        return tutorialModels;
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
}

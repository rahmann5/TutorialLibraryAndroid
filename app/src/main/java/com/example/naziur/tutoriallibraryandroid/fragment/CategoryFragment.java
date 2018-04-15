package com.example.naziur.tutoriallibraryandroid.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.adapters.CategoryAdapter;
import com.example.naziur.tutoriallibraryandroid.model.CategoryModel;
import com.example.naziur.tutoriallibraryandroid.utility.ServerRequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends MainFragment implements ServerRequestManager.OnRequestCompleteListener{
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CategoryAdapter categoryAdapter;
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
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ServerRequestManager.setOnRequestCompleteListener(this);
        ServerRequestManager.getAllTags(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.all_recycle_view);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView () {
        categoryAdapter = new CategoryAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(categoryAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }



    private ArrayList<CategoryModel> loadCategoriesData (String... s) {
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();
        try {
            JSONArray allTags = new JSONArray(s[0]);
            categoryModels = verifyLetterChange(allTags);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryModels;
    }

    private ArrayList<CategoryModel> verifyLetterChange (JSONArray allTags) throws JSONException {
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();
        for (int i = 0; i < allTags.length(); i++) {
            char currentFirstLetter = allTags.getJSONObject(i).getString("tag").charAt(0);
            if (i > 0) {
                char prevFirstLetter = allTags.getJSONObject(i-1).getString("tag").charAt(0);
                if (!String.valueOf(prevFirstLetter).
                        equalsIgnoreCase(String.valueOf(currentFirstLetter))) {
                    categoryModels.add(new CategoryModel(String.valueOf(currentFirstLetter)));
                    categoryModels.add(new CategoryModel(allTags.getJSONObject(i).getString("tag"), allTags.getJSONObject(i).getString("id")));
                } else {
                    categoryModels.add(new CategoryModel(allTags.getJSONObject(i).getString("tag"), allTags.getJSONObject(i).getString("id")));
                }
            } else {
                categoryModels.add(new CategoryModel(String.valueOf(currentFirstLetter)));
                categoryModels.add(new CategoryModel(allTags.getJSONObject(i).getString("tag"), allTags.getJSONObject(i).getString("id")));
            }
        }
        return categoryModels;
    }

    @Override
    public void onSuccessfulRequestListener(String command, String... s) {
        switch (command) {
            case ServerRequestManager.COMMAND_All_TAGS :
                categoryAdapter.setCategoryData(loadCategoriesData(s));
                break;

        }
    }

    @Override
    public void onFailedRequestListener(String command, String... s) {
        switch (command) {
            case ServerRequestManager.COMMAND_All_TAGS :
                Toast.makeText(getActivity(), "ERROR " + s[0], Toast.LENGTH_LONG).show();
                break;

        }
    }
}

package com.example.naziur.tutoriallibraryandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naziur.tutoriallibraryandroid.MainActivity;
import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.adapters.ReferenceAdapter;
import com.example.naziur.tutoriallibraryandroid.adapters.TagsAdapter;
import com.example.naziur.tutoriallibraryandroid.model.SectionModel;
import com.example.naziur.tutoriallibraryandroid.model.TagModel;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.adapters.SectionAdapter;
import com.example.naziur.tutoriallibraryandroid.utility.Constants;
import com.example.naziur.tutoriallibraryandroid.utility.ServerRequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.example.naziur.tutoriallibraryandroid.utility.Constants.ASSETS_URL_IMG_DEFULT;
import static com.example.naziur.tutoriallibraryandroid.utility.Constants.ASSETS_URL_IMG_DIR;
import static com.example.naziur.tutoriallibraryandroid.utility.Constants.FRAGMENT_KEY_TUT_ID;
import static com.example.naziur.tutoriallibraryandroid.utility.Constants.RANDOM;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialViewerFragment extends MainFragment implements ServerRequestManager.OnRequestCompleteListener{

    private RecyclerView sectionRecyclerView, tagsRecyclerView, referenceRecyclerView;
    private SectionAdapter sectionAdapter;
    private TagsAdapter tagsAdapter;
    private ReferenceAdapter refsAdapter;
    private TutorialModel tutorialModel;
    private View view;
    private Button onlineButton;

    public TutorialViewerFragment() {
        // Required empty public constructor
    }

    public static TutorialViewerFragment newInstance() {
        TutorialViewerFragment tutorialViewerFragment = new TutorialViewerFragment();
        return tutorialViewerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tutorial_viewer, container, false);
        ServerRequestManager.setOnRequestCompleteListener(this);
        // need to receive bundal extra
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ServerRequestManager.getTutorial(getActivity(), bundle.getString(FRAGMENT_KEY_TUT_ID));
        }

        sectionRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutorial_section_viewer);
        tagsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutorial_tags);
        referenceRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutorial_refs);
        onlineButton = (Button) view.findViewById(R.id.online_btn);

        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), tutorialModel.getTitle() , Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void setUpRecyclerViews () {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        sectionAdapter = new SectionAdapter(getActivity(), tutorialModel.getSections());
        sectionRecyclerView.setLayoutManager(mLayoutManager);
        sectionRecyclerView.setAdapter(sectionAdapter);
        sectionRecyclerView.setNestedScrollingEnabled(false);
        sectionRecyclerView.setHasFixedSize(true);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        tagsAdapter = new TagsAdapter(getActivity(), tutorialModel.getTags());
        tagsRecyclerView.setLayoutManager(horizontalLayoutManager);
        tagsRecyclerView.setHasFixedSize(true);
        tagsRecyclerView.setAdapter(tagsAdapter);

        LinearLayoutManager refLayoutManager = new LinearLayoutManager(getActivity());
        refsAdapter = new ReferenceAdapter(getActivity(), tutorialModel.getReferences());
        referenceRecyclerView.setLayoutManager(refLayoutManager);
        referenceRecyclerView.setAdapter(refsAdapter);
        referenceRecyclerView.setNestedScrollingEnabled(false);
        referenceRecyclerView.setHasFixedSize(true);
        referenceRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    private void loadData (String jsonString) {
        String title = "";
        String author = "";
        String intro = "";
        String created = "Unknown";
        String intro_img = ASSETS_URL_IMG_DEFULT + "default_img.jpg";
        SectionModel [] allSections = new SectionModel[0];
        TagModel [] allTags = new TagModel[0];
        String [] allRefs = new String [0];
        try {
            JSONObject tutorialJson = new JSONObject(jsonString);
            title = tutorialJson.getString("title");
            author = tutorialJson.getString("author");
            intro = tutorialJson.getString("intro");
            created = tutorialJson.getString("created");
            intro_img = ASSETS_URL_IMG_DIR + tutorialJson.getString("intro_img");
            JSONArray segments = tutorialJson.getJSONArray("segments");
            allSections = new SectionModel[segments.length()];
            for (int i = 0; i < segments.length(); i++) {
                JSONObject segmentObj = segments.getJSONObject(i);
                allSections[i] = new SectionModel(segmentObj.getString("heading"), segmentObj.getString("paragraph"), ASSETS_URL_IMG_DIR + segmentObj.getString("image"));
            }
            JSONArray tags = tutorialJson.getJSONArray("tags");
            allTags =  new TagModel[tags.length()];
            for (int i = 0; i < tags.length(); i++) {
                JSONObject tag = tags.getJSONObject(i);
                Iterator keys = tag.keys();
                if (keys.hasNext()) {
                    String key = (String)keys.next();
                    allTags[i] = new TagModel(tag.getString(key), key);
                }
            }

            JSONArray refs = tutorialJson.getJSONArray("references");
            allRefs = new String[refs.length()];
            for (int i = 0 ; i < refs.length(); i++) {
                allRefs[i] = refs.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tutorialModel = new TutorialModel(title, author, intro, intro_img, created, allTags, allSections, allRefs);
        displayTutorial();
        setUpRecyclerViews();
    }

    private void displayTutorial () {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(tutorialModel.getTitle());
        ((TextView) view.findViewById(R.id.tutorial_author)).setText("By: " + tutorialModel.getAuthor());
        ((TextView) view.findViewById(R.id.tutorial_date)).setText(tutorialModel.getCreatedAtDate());
        ((TextView) view.findViewById(R.id.tutorial_intro)).setText(tutorialModel.getIntro());
        Glide.with(getActivity()).load(tutorialModel.getIntroImageUrl()).into(((ImageView) view.findViewById(R.id.tutorial_intro_img)));
    }

    @Override
    public void onSuccessfulRequestListener(String command, String... s) {
        switch (command) {
            case ServerRequestManager.COMMAND_SINGLE_TUTORIAL :
                loadData(s[0]);
                onlineButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onFailedRequestListener(String command, String... s) {
        switch (command) {
            case ServerRequestManager.COMMAND_SINGLE_TUTORIAL :
                Toast.makeText(getActivity(), "ERROR " + s[0], Toast.LENGTH_LONG).show();
                break;
        }
    }
}

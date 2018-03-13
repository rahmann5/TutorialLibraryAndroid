package com.example.naziur.tutoriallibraryandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.naziur.tutoriallibraryandroid.MainActivity;
import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.adapters.ReferenceAdapter;
import com.example.naziur.tutoriallibraryandroid.adapters.TagsAdapter;
import com.example.naziur.tutoriallibraryandroid.model.SectionModel;
import com.example.naziur.tutoriallibraryandroid.model.TagModel;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.adapters.SectionAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialViewerFragment extends MainFragment {

    private RecyclerView sectionRecyclerView, tagsRecyclerView, referenceRecyclerView;
    private SectionAdapter sectionAdapter;
    private TagsAdapter tagsAdapter;
    private ReferenceAdapter refsAdapter;
    private TutorialModel tutorialModel;

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
        loadData ();

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(tutorialModel.getTitle());

        View view = inflater.inflate(R.layout.fragment_tutorial_viewer, container, false);

        ((TextView) view.findViewById(R.id.tutorial_author)).setText("By: " + tutorialModel.getAuthor());
        ((TextView) view.findViewById(R.id.tutorial_date)).setText(tutorialModel.getCreatedAtDate());
        ((TextView) view.findViewById(R.id.tutorial_intro)).setText(tutorialModel.getIntro());
        Glide.with(getActivity()).load(tutorialModel.getIntroImageUrl()).into(((ImageView) view.findViewById(R.id.tutorial_intro_img)));

        sectionRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutorial_section_viewer);
        tagsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutorial_tags);
        referenceRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutorial_refs);
        setUpRecyclerViews();
        return view;
    }

    private void setUpRecyclerViews () {
        sectionAdapter = new SectionAdapter(getActivity(), tutorialModel.getSections());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        sectionRecyclerView.setLayoutManager(mLayoutManager);
        sectionRecyclerView.setAdapter(sectionAdapter);
        sectionRecyclerView.setNestedScrollingEnabled(false);
        sectionRecyclerView.setHasFixedSize(true);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        tagsAdapter = new TagsAdapter(getActivity(), tutorialModel.getTags());
        tagsRecyclerView.setLayoutManager(horizontalLayoutManager);
        tagsRecyclerView.setHasFixedSize(true);
        tagsRecyclerView.setAdapter(tagsAdapter);

        refsAdapter = new ReferenceAdapter(getActivity(), tutorialModel.getReferences());
        LinearLayoutManager refLayoutManager = new LinearLayoutManager(getActivity());
        referenceRecyclerView.setLayoutManager(refLayoutManager);
        referenceRecyclerView.setAdapter(refsAdapter);
        referenceRecyclerView.setNestedScrollingEnabled(false);
        referenceRecyclerView.setHasFixedSize(true);
        referenceRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    private void loadData () {
        // get the extra bundle called the tutorial id
        // need to use tutorial id to get json file from server
        // on success load data

        SectionModel [] allSections = new SectionModel[2];
        allSections[0] = new SectionModel("Header 1", "Section 1 information", "http://tutoriallibrary.000webhostapp.com/assets/images/tutorials/10/1.jpg");
        allSections[1] = new SectionModel("Header 2", "Section 2 information", "http://tutoriallibrary.000webhostapp.com/assets/images/tutorials/10/2.png");

        TagModel [] allTags = new TagModel[]{new TagModel("Lifestyle", "3"), new TagModel("Android", "1")};
        String [] allRefs = new String [] {"Barroth ecology", "Book of lies", "This book plagiarises"};

        tutorialModel = new TutorialModel("A test title", "Mr Author", "A short intro for test", "http://tutoriallibrary.000webhostapp.com/assets/images/tutorials/10/intro-image.jpg", "08/12/1874", allTags, allSections, allRefs);

    }

}

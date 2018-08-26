package com.example.naziur.tutoriallibraryandroid.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.adapters.TutorialAdapter;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.utility.Constants;
import com.example.naziur.tutoriallibraryandroid.utility.ProgressDialog;
import com.example.naziur.tutoriallibraryandroid.utility.ServerRequestManager;
import com.example.naziur.tutoriallibraryandroid.utility.TutorialLoader;

import java.util.List;

import static com.example.naziur.tutoriallibraryandroid.utility.Constants.FRAGMENT_KEY_TUT_ID;

/**
 * A simple {@link Fragment} subclass.
 *
 * [[{"tid":"10","slug":"Java-Brogramming-beginner","author_id":"1","title":"Java Brogramming beginner","img":"tutorials\/10\/intro-image.jpg","intro":"<p>Learn the basics of Java<\/p>","created":"2018-02-18 19:21:44","aid":"1","name":"test","email":"test@gmail.com","password":"c4ca4238a0b923820dcc509a6f75849b","registeration_date":"2018-01-28 13:19:09"},{"tid":"16","slug":"Making-your-very-own-Butorial","author_id":"1","title":"Making your very own Butorial","img":"tutorials\/16\/intro-image.jpg","intro":"<p>A butorial is a tutorial but involves a brute wyvern and a mangoose<\/p>","created":"2018-03-03 11:23:07","aid":"1","name":"test","email":"test@gmail.com","password":"c4ca4238a0b923820dcc509a6f75849b","registeration_date":"2018-01-28 13:19:09"},{"tid":"17","slug":"How-to-educate-yourself","author_id":"1","title":"How to educate yourself","img":"tutorials\/17\/intro-image.jpg","intro":"<p>This is a tutorial on how to educate youself.<\/p>","created":"2018-03-03 13:36:34","aid":"1","name":"test","email":"test@gmail.com","password":"c4ca4238a0b923820dcc509a6f75849b","registeration_date":"2018-01-28 13:19:09"},{"tid":"18","slug":"How-to-use-a-calculator","author_id":"1","title":"How to use a calculator","img":"tutorials\/18\/intro-image.png","intro":"<p>In this tutorial I will teach you how to use these <strong>Four<\/strong> features of a calculator:<\/p><ol><li>Addition<\/li><li>Subtraction<\/li><li>Multiplication<\/li><li>Division<\/li><\/ol>","created":"2018-04-05 11:25:25","aid":"1","name":"test","email":"test@gmail.com","password":"c4ca4238a0b923820dcc509a6f75849b","registeration_date":"2018-01-28 13:19:09"}],{"10":[["Java","2"],["Life Style","7"]],"16":[["Android","1"],["Java","2"],["Testing","8"]],"17":[["Life Style","7"]],"18":[["Life Style","7"]]}]
 *
 */
public class TutorialsFragment extends MainFragment implements LoaderManager.LoaderCallbacks<List<TutorialModel>>, ServerRequestManager.OnRequestCompleteListener{

    private TutorialAdapter tutorialAdapter;
    private LinearLayoutManager mLayoutManager;
    private String tagId;
    private String json;

    private static final int TUTORIAL_LOADER_ID = 25;

    public static TutorialsFragment newInstance() {
        TutorialsFragment tutorialsFragment = new TutorialsFragment();
        return tutorialsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        setActionBarTitle(getString(R.string.title_all_tuts));
        setComponentVisibleListener();
        componentVisibleListener.resetLayout();
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tutorials, container, false);
        ServerRequestManager.setOnRequestCompleteListener(this);
        progressDialog = new ProgressDialog(getActivity(), R.layout.progress_dialog, true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tagId = bundle.getString(Constants.FRAGMENT_KEY_TAG_ID);
            this.getArguments().clear();
        }

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
                    }
                }
            }
        });
        RecyclerView mRecyclerView = rootView.findViewById(R.id.all_recycle_view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(tutorialAdapter);
        progressDialog.toggleDialog(true);
        if(tagId == null)
            ServerRequestManager.getTutorials(getContext());
        else
            ServerRequestManager.getAllTutorialsForTag(getContext(), tagId);
        return rootView;
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

    @Override
    public Loader<List<TutorialModel>> onCreateLoader(int id, Bundle args) {
        return new TutorialLoader(getContext(), json);
    }

    @Override
    public void onLoadFinished(Loader<List<TutorialModel>> loader, List<TutorialModel> data) {
        tutorialAdapter.clear();
        tutorialAdapter.setTutorialModels(data);
        tutorialAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<TutorialModel>> loader) {
        // Loader reset, so we can clear out our existing data.
        tutorialAdapter.clear();
    }

    @Override
    public void onSuccessfulRequestListener(String command, String... s) {
        switch(command){
            case ServerRequestManager.COMMAND_GET_ALL_TUTORIALS:
            case ServerRequestManager.COMMAND_TUTORIALS_FOR_TAG:
                progressDialog.toggleDialog(false);
                json = s[0];
                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getActivity().getLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.restartLoader(TUTORIAL_LOADER_ID, null, this);
                    componentVisibleListener.onErrorFound(false, "");
                } else {
                    // Otherwise, display error
                    componentVisibleListener.onErrorFound(true, "No network connection");
                }
                break;
            default: componentVisibleListener.onErrorFound(true, s[0]);
                break;

        }
    }

    @Override
    public void onFailedRequestListener(String command, String... s) {
        progressDialog.toggleDialog(false);
        componentVisibleListener.onErrorFound(true, s[0]);
    }
}

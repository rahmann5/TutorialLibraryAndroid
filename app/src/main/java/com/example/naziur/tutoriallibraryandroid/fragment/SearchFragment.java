package com.example.naziur.tutoriallibraryandroid.fragment;


import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.adapters.TutorialAdapter;
import com.example.naziur.tutoriallibraryandroid.model.TutorialModel;
import com.example.naziur.tutoriallibraryandroid.utility.ProgressDialog;
import com.example.naziur.tutoriallibraryandroid.utility.ServerRequestManager;
import com.example.naziur.tutoriallibraryandroid.utility.TutorialLoader;

import java.util.List;

import static com.example.naziur.tutoriallibraryandroid.utility.Constants.FRAGMENT_KEY_TAG_ID;
import static com.example.naziur.tutoriallibraryandroid.utility.Constants.FRAGMENT_KEY_TUT_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends MainFragment implements ServerRequestManager.OnRequestCompleteListener, LoaderManager.LoaderCallbacks<List<TutorialModel>>{

    private TutorialAdapter mTutorialAdapter;
    private LinearLayoutManager mLayoutManager;
    private String json;
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int SEARCH_TUTORIAL_LOADER_ID = 1;
    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(){
        SearchFragment searchFragment = new SearchFragment();
        return searchFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        setActionBarTitle(getString(R.string.title_search));
        setComponentVisibleListener();
        componentVisibleListener.resetLayout();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        progressDialog = new ProgressDialog(getActivity(), R.layout.progress_dialog, true);
        RecyclerView mRecyclerView = view.findViewById(R.id.result_recycle_view);
        mTutorialAdapter = new TutorialAdapter(getContext(), new TutorialAdapter.ViewClickListener() {
            @Override
            public void onViewClick(boolean isTutorial, String id) {
                if(isTutorial) {
                    Bundle args = new Bundle();
                    args.putString(FRAGMENT_KEY_TUT_ID, id);
                    Fragment fragment = new TutorialViewerFragment();
                    switchFragment(fragment, args);
                } else {
                    Bundle args = new Bundle();
                    args.putString(FRAGMENT_KEY_TAG_ID, id);
                    Fragment fragment = new TutorialsFragment();
                    switchFragment(fragment, args);
                }

            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mTutorialAdapter);
        setHasOptionsMenu(true);
        ServerRequestManager.setOnRequestCompleteListener(this);
        return view;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) item.getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(getContext().SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query, spinner.getSelectedItem().toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void performSearch(String query, String spinnerData){
        ServerRequestManager.getTutorialSearchResult(getContext(), query, spinnerData);
        progressDialog.toggleDialog(true);
    }

    @Override
    public void onSuccessfulRequestListener(String command, String... s) {
        switch (command){
            case ServerRequestManager.COMMAND_SEARCH_FOR_TUTORIAL:
                progressDialog.toggleDialog(false);
                json = s[0];
                //System.out.println(json);
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
                    loaderManager.restartLoader(SEARCH_TUTORIAL_LOADER_ID, null, this);
                    componentVisibleListener.onErrorFound(false, "");
                } else {
                    // Otherwise, display error
                    componentVisibleListener.onErrorFound(true, "No network connection");
                }
        }
    }

    @Override
    public void onFailedRequestListener(String command, String... s) {
        progressDialog.toggleDialog(false);
        componentVisibleListener.onErrorFound(true, s[0]);
    }

    @Override
    public Loader<List<TutorialModel>> onCreateLoader(int i, Bundle bundle) {
        return new TutorialLoader(getContext(), json);
    }

    @Override
    public void onLoadFinished(Loader<List<TutorialModel>> loader, List<TutorialModel> tutorialModels) {
        mTutorialAdapter.setTutorialModels(tutorialModels);
        mTutorialAdapter.notifyDataSetChanged();
        if(tutorialModels.size() > 0)
            componentVisibleListener.onErrorFound(false, "");
        else
            componentVisibleListener.onErrorFound(true, getString(R.string.no_result));
        progressDialog.toggleDialog(false);
    }

    @Override
    public void onLoaderReset(Loader<List<TutorialModel>> loader) {
        // Loader reset, so we can clear out our existing data.
        mTutorialAdapter.clear();
        progressDialog.toggleDialog(false);
    }
}

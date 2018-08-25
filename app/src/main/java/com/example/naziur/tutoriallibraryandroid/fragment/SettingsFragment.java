package com.example.naziur.tutoriallibraryandroid.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.utility.LocaleManager;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends MainFragment{

    public static SettingsFragment newInstance(){
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setActionBarTitle(getString(R.string.title_settings));
        setComponentVisibleListener();

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        final Spinner langSpinner = (Spinner) v.findViewById(R.id.lang_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.spinner_list_item_array_2));
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        langSpinner.setAdapter(adapter);
        langSpinner.setSelection(adapter.getPosition(LocaleManager.getLocale(getActivity().getResources()).getLanguage().toUpperCase()));
        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean start = false;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (start) {

                    String item = langSpinner.getItemAtPosition(i).toString().toLowerCase();
                    setNewLocale(item);

                }
                start = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        componentVisibleListener.onErrorFound(false, "");

        return v;
    }

    private void setNewLocale(String language) {
        LocaleManager.setNewLocale(getActivity(), language);
        Intent i = getActivity().getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getActivity().getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}

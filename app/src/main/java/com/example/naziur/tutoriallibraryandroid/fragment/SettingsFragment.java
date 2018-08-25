package com.example.naziur.tutoriallibraryandroid.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.naziur.tutoriallibraryandroid.MainActivity;
import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.utility.Constants;
import com.example.naziur.tutoriallibraryandroid.utility.ThemeManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends MainFragment{

    private Spinner spinner;
    private boolean themeChanged;

    public static SettingsFragment newInstance(){
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        spinner = (Spinner) v.findViewById(R.id.theme_spinner);
        themeChanged = false;
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(), R.array.themes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setSelection(sharedPref.getInt(Constants.THEME_PREF_KEY, 0));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int count = 0;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(count >0) {

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(Constants.THEME_PREF_KEY, i);
                    editor.commit();
                    themeChanged = true;

                }
                count++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(themeChanged) {
            ThemeManager.changeToTheme(getActivity());
        }
    }

}

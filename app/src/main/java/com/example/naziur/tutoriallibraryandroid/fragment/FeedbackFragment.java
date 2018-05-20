package com.example.naziur.tutoriallibraryandroid.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.naziur.tutoriallibraryandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends MainFragment {


    public static FeedbackFragment newInstance() {
        FeedbackFragment feedbackFragment = new FeedbackFragment();
        return feedbackFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        Button rateNow = (Button) v.findViewById(R.id.rate_now);
        rateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +  getContext().getPackageName())));
                getFragmentManager().popBackStack();
            }
        });

        Button rateLater = (Button) v.findViewById(R.id.rate_later);
        rateLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        Button rateNever = (Button) v.findViewById(R.id.rate_never);
        rateNever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getActivity().getSharedPreferences("apprater", 0);
                SharedPreferences.Editor editor = prefs.edit();
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                getFragmentManager().popBackStack();
            }
        });

        return v;
    }

}

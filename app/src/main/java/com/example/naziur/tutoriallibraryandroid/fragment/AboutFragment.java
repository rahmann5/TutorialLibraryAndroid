package com.example.naziur.tutoriallibraryandroid.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naziur.tutoriallibraryandroid.R;
import com.example.naziur.tutoriallibraryandroid.utility.UlTagHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends MainFragment {


    public static AboutFragment newInstance() {
        AboutFragment aboutFragment = new AboutFragment();
        return aboutFragment;
    }

    public AboutFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NewAPi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarTitle(getString(R.string.title_about));
        setComponentVisibleListener();
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ImageView iv  = (ImageView)rootView.findViewById(R.id.expandedImage);
        Drawable dr = ContextCompat.getDrawable(getContext(), R.drawable.about_banner);
        Bitmap d = ((BitmapDrawable) dr).getBitmap();
        int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
        iv.setImageBitmap(scaled);

        //Set toolbar title
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Browse. Click. Learn");
        collapsingToolbar.setExpandedTitleGravity(Gravity.TOP);
        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= 11) {
            collapsingToolbar.setExpandedTitleColor(getActivity().getResources().getColor(R.color.white, null));
        }
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        TextView aimsTv = (TextView) rootView.findViewById(R.id.aims_contents);
        WebView webWv = rootView.findViewById(R.id.website_contents);
        WebView authorsWv = rootView.findViewById(R.id.author_contents);
        aimsTv.setText(fromHtml(R.string.aims_html));
        webWv.setBackgroundColor(Color.TRANSPARENT);
        webWv.loadDataWithBaseURL(null, getString(R.string.website_html), "text/html", "utf-8", null);
        authorsWv.setBackgroundColor(Color.TRANSPARENT);
        authorsWv.loadDataWithBaseURL(null, getString(R.string.authors_html), "text/html", "utf-8", null);
        componentVisibleListener.onErrorFound(false, "");
        return rootView;
    }

    private Spanned fromHtml(int stringId){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(getString(stringId), Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(getString(stringId), null, new UlTagHandler());
        }
    }

}

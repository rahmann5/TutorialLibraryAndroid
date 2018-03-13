package com.example.naziur.tutoriallibraryandroid.utility;

/**
 * Created by Hamidur on 13/03/2018.
 */

public class ServerRequestManager {
    private static final String END_POINT = "https://tutoriallibrary.000webhostapp.com/app_request/process_request/";
    private static final String APP_KEY = "c4ca4238a0b923820dcc509a6f75849b";

    private static OnRequestCompleteListener onRequestCompleteListener;

    public interface OnRequestCompleteListener {
        void onSuccessfulRequestListener (String command, String...s);
        void onFailedRequestListener (String command, String...s);
    }

    public static void setOnRequestCompleteListener (OnRequestCompleteListener listener) {
        onRequestCompleteListener = listener;
    }



}

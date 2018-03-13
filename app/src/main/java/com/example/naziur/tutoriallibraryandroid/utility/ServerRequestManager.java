package com.example.naziur.tutoriallibraryandroid.utility;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Hamidur on 13/03/2018.
 */

public class ServerRequestManager {
    private static final String END_POINT = "https://tutoriallibrary.000webhostapp.com/apicall/process_request/";
    private static final String APP_KEY = "c4ca4238a0b923820dcc509a6f75849b";

    private static final String APP_VALUE = "app_key";

    private static OnRequestCompleteListener onRequestCompleteListener;

    private ServerRequestManager () {

    }

    public interface OnRequestCompleteListener {
        void onSuccessfulRequestListener (String command, String...s);
        void onFailedRequestListener (String command, String...s);
    }

    public static void setOnRequestCompleteListener (OnRequestCompleteListener listener) {
        onRequestCompleteListener = listener;
    }

    public static void getTutorial (Context context, final String tutorialId) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, END_POINT + "tutorial", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onRequestCompleteListener.onSuccessfulRequestListener("tutorial", response);
                System.out.print(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRequestCompleteListener.onFailedRequestListener("tutorial", error.getMessage());
                System.out.print("ERROR");
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(APP_VALUE, APP_KEY);
                params.put("tutorial_id", tutorialId);
                return params;
            }
        };

        createRequest(stringRequest, context);
    }

    public static void getTutorials(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, END_POINT + "all_tutorials", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                params.put(APP_VALUE, APP_KEY);
                //returning parameters
                return params;
            }
        };

        createRequest(stringRequest, context);
    }

    private static void createRequest (StringRequest request, Context context) {
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(request);
    }

}

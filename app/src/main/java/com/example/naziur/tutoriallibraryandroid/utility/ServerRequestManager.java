package com.example.naziur.tutoriallibraryandroid.utility;

import android.content.Context;
import android.widget.Toast;

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
    public static final String SERVER_URL = "https://tutoriallibrary.000webhostapp.com";
    private static final String END_POINT = SERVER_URL+"/apicall/";

    private static final String APP_KEY = "c4ca4238a0b923820dcc509a6f75849b";

    private static final String APP_VALUE = "app_key";

    public static final String COMMAND_All_TAGS = "get_all_tags";
    public static final String COMMAND_GET_ALL_TUTORIALS = "get_all_tutorials";
    public static final String COMMAND_SINGLE_TUTORIAL = "get_a_tutorial";
    public static final String COMMAND_TUTORIALS_FOR_TAG = "get_tutorials_for_tag";
    public static final String COMMAND_SEARCH_FOR_TUTORIAL = "search_for_tutorial";

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
        StringRequest stringRequest= new StringRequest(Request.Method.POST, END_POINT + COMMAND_SINGLE_TUTORIAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onRequestCompleteListener.onSuccessfulRequestListener(COMMAND_SINGLE_TUTORIAL, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRequestCompleteListener.onFailedRequestListener(COMMAND_SINGLE_TUTORIAL, error.toString());
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

    public static void getAllTags (Context context) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, END_POINT + COMMAND_All_TAGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onRequestCompleteListener.onSuccessfulRequestListener(COMMAND_All_TAGS, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRequestCompleteListener.onFailedRequestListener(COMMAND_All_TAGS, error.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(APP_VALUE, APP_KEY);
                return params;
            }
        };

        createRequest(stringRequest, context);
    }

    public static void getTutorials(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, END_POINT + COMMAND_GET_ALL_TUTORIALS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onRequestCompleteListener.onSuccessfulRequestListener(COMMAND_GET_ALL_TUTORIALS, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onRequestCompleteListener.onFailedRequestListener("tutorials", volleyError.getMessage());
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

    public static void getAllTutorialsForTag(Context context, final String tagId){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, END_POINT + COMMAND_TUTORIALS_FOR_TAG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onRequestCompleteListener.onSuccessfulRequestListener(COMMAND_TUTORIALS_FOR_TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRequestCompleteListener.onFailedRequestListener(COMMAND_TUTORIALS_FOR_TAG, error.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(APP_VALUE, APP_KEY);
                params.put("tag_id", tagId);
                return params;
            }
        };

        createRequest(stringRequest, context);
    }

    public static void getTutorialSearchResult(Context context, final String searchString, final String searchType){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, END_POINT + COMMAND_SEARCH_FOR_TUTORIAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onRequestCompleteListener.onSuccessfulRequestListener(COMMAND_SEARCH_FOR_TUTORIAL, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRequestCompleteListener.onFailedRequestListener(COMMAND_SEARCH_FOR_TUTORIAL, error.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put(APP_VALUE, APP_KEY);
                params.put("search", searchString);
                params.put("filter", searchType);
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

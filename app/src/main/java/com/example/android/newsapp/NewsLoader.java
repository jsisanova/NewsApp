package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import static com.example.android.newsapp.QueryUtils.fetchNewsStoryData;

/**
 * Loads a list of news by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {
    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
//        Log.i(LOG_TAG, "Test: Loaded!");
    }


    @Override
    // This method is triggered automatically from initLoader
    protected void onStartLoading() {
//        Log.i(LOG_TAG, "Test: call onStartLoading()");
        // Required to trigger the loader to start doing the background work (to trigger loadInBackground())
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
//        Log.i(LOG_TAG, "Test: call loadInBackground()");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of newses as a result of a loader
        List<News> newses = fetchNewsStoryData(mUrl);
        return newses;
    }
}
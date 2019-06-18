package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    /** URL for news data from the Guardian dataset */
    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/search?from-date=2009-01-01&q=tennis&show-tags=contributor&api-key=3e99049a-6fcd-443b-aa1f-9448702c46c4";
//    public final static String NEWS_REQUEST_URL = "https://content.guardianapis.com/search?show-fields=trailText&show-tags=contributor";


    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This only comes into play if we're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /** Tag for log messages */
    public static final String LOG_TAG = MainActivity.class.getName();

    /** Adapter for the list of news */
    private NewsAdapter adapter;

    /** TextView that is displayed when the list is empty */
    private LinearLayout mEmptyStateLinearLayout;
    private TextView mEmptyStateTextViewFirstLine;
    private TextView mEmptyStateTextViewSecondLine;

    /** Loading indicator only shown before the first load */
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.i(LOG_TAG, "Test: onCreate() method called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);
        // Create a new adapter that takes an empty list of earthquakes as input
        adapter = new NewsAdapter(this, new ArrayList<News>());
        // Attach the adapter on the {@link ListView} so the list can be populated in the UI
        newsListView.setAdapter(adapter);

        // Access the URL when the list item is clicked
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                // Find the current article that was clicked on
                News currentNews = adapter.getItem(position);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                // Get the URL from the current News object.
                // The Intent constructor (that we want to use) requires a Uri object, so we need to convert
                // our URL (in the form of a String) into a URI.
                Uri newsUri = Uri.parse(currentNews.getArticleUrl());
                // Create a new intent to view the news URI
                // Once we have the website URL in a Uri object, we can create a new intent.
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                // Send the intent to launch a new activity.
                // Start a new activity with that intent, so that a web browser app on the device
                // will handle the intent and display the website for that news.
                startActivity(websiteIntent);
            }
        });

        // If empty state of the list - set TextView as the empty view of the ListView.
        mEmptyStateLinearLayout = (LinearLayout) findViewById(R.id.empty_view);
        mEmptyStateTextViewFirstLine = (TextView) findViewById(R.id.empty_view_text_first_line);
        mEmptyStateTextViewSecondLine = (TextView) findViewById(R.id.empty_view_text_second_line);
        newsListView.setEmptyView(mEmptyStateLinearLayout);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle of additional information.
            // The third argument is what object should receive the LoaderCallbacks (and therefore, the data when
            // the load is complete!) - which will be this activity for the LoaderCallbacks parameter
            // (which is valid because this activity implements the LoaderCallbacks interface) = hence the activity
            // itself is the LoaderCallback object that we want to pass in.
//            Log.i(LOG_TAG, "Test: call initLoader()");
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextViewFirstLine.setText(R.string.no_internet_connection);
        }
    }

    @Override
    // Create a new Loader object for the given URL (to fetch news data)
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
//        Log.i(LOG_TAG, "Test: call onCreateLoader()");
        return new NewsLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    // This method is called when loader finishes loading data on background thread
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
//        Log.i(LOG_TAG, "Test: call onLoadFinished()");

        // Hide loading indicator because the data has been loaded
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No updates for the moment, check back later!"
        mEmptyStateTextViewFirstLine.setText(R.string.no_updates_first_line);
        mEmptyStateTextViewSecondLine.setText(R.string.no_updates_second_line);

        // Clear the adapter of previous news data
        adapter.clear();

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
    }

    @Override
    // Reset the previous created loader, so we can clear out our existing data.
    // Then the data from our loader are no longer valid.
    public void onLoaderReset(Loader<List<News>> loader) {
//        Log.i(LOG_TAG, "Test: call onLoaderReset()");
        adapter.clear();
    }


    // Inflate the Menu we specified in the main.xml when the MainActivity opens up, and display the menu in the app bar.
    @Override
    // Initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Open the SettingsActivity when the user selects the Options Menu
    @Override
    // Pass the MenuItem that is selected
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine which item was selected and what action to take
        int id = item.getItemId();
        // Match the ID against known menu items to open the SettingsActivity via an intent.
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.example.android.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Setup a preference in the SettingsActivity the user can edit
            addPreferencesFromResource(R.xml.settings_main);

            // Update the preference summary (the UI) when the SettingsActivity is launched
            Preference pageSize = findPreference(getString(R.string.settings_page_size_key));
            bindPreferenceSummaryToValue(pageSize);

            // Update with orderBy preference
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
        }

        @Override
        // Classes that implement Preference.OnPreferenceChangeListener interface are setup to listen for any Preference changes made by the user.
        // Update the displayed preference summary after it has been changed
        public boolean onPreferenceChange(Preference preference, Object value) {
            //  Take in the preference value, convert it to a String
            String stringValue = value.toString();
            // Properly update the summary of a ListPreference (using the label, instead of the key):
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                // Display stringValue in the summary UI (e.g. by EditText)
                preference.setSummary(stringValue);
            }
            return true;
        }

        // Helper method (used in onCreate()), in order to update the preference summary when the settings activity is launched
        private void bindPreferenceSummaryToValue(Preference preference) {
            // Set the current EarthquakePreferenceFragment instance to listen for changes to the preference we pass in
            preference.setOnPreferenceChangeListener(this);
            // Read the current value of the preference stored in the SharedPreferences on the device,
            // and display that in the preference summary (so that the user can see the current value of the preference)
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
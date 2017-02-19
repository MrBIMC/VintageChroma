package com.kunzisoft.androidclearchroma.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kunzisoft.androidclearchroma.ChromaPreferenceCompat;
import com.kunzisoft.androidclearchroma.ChromaPreferenceFragmentCompat;
import com.kunzisoft.androidclearchroma.IndicatorMode;
import com.kunzisoft.androidclearchroma.colormode.ColorMode;

/**
 * Sample Activity for show chroma preferences with compatibility class
 */
public class PreferencesCompatActivity extends AppCompatActivity {

    private static final String TAG_PREFERENCE_FRAGMENT = "TAG_PREFERENCE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Manage fragment who contains list of preferences
        ChromaPreferenceFragmentCompat chromaPreferenceFragmentCompat =
                (ChromaPreferenceFragmentCompat) getSupportFragmentManager().findFragmentByTag(TAG_PREFERENCE_FRAGMENT);

        if(chromaPreferenceFragmentCompat == null)
            chromaPreferenceFragmentCompat = new ColorPreferenceFragmentCompat();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, chromaPreferenceFragmentCompat, TAG_PREFERENCE_FRAGMENT)
                .commit();

        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Tiny class inherited from ChromaPreferenceFragmentCompat who manage chroma preferences and callback of DialogFragment
     */
    public static class ColorPreferenceFragmentCompat extends ChromaPreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(com.kunzisoft.androidclearchroma.R.xml.prefs_v7); // load your ChromaPreferenceCompat prefs from xml

            //or add them manually:
            ChromaPreferenceCompat pref = new ChromaPreferenceCompat(getActivity());
            pref.setTitle("RGB(added from java)");
            pref.setColorMode(ColorMode.RGB);
            pref.setIndicatorMode(IndicatorMode.HEX);
            pref.setKey("any_key_you_need");
            getPreferenceScreen().addPreference(pref);
        }
    }
}

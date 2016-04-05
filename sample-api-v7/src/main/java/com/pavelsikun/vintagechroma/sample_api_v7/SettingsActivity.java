package com.pavelsikun.vintagechroma.sample_api_v7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;
import android.view.View;

import com.pavelsikun.vintagechroma.ChromaPreferenceCompat;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

/**
 * Created by Pavel Sikun on 5.04.16.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new PreferencesScreen())
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

    public static class PreferencesScreen extends PreferenceFragmentCompat {

        //I also recommend you to use some 3rd-party lib to style PreferenceFragmentCompat
        //because by dafault v7 prefs don't respect device theme and look ugly, evem on v21+
        //theoretically useful links:
        // * http://stackoverflow.com/questions/32070670/preferencefragmentcompat-requires-preferencetheme-to-be-set
        // * https://github.com/consp1racy/android-support-preference

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.pref_general); // load your ChromaPreferenceCompat prefs from xml

            //or add them manually:
            ChromaPreferenceCompat pref = new ChromaPreferenceCompat(getActivity());
            pref.setTitle("RGB(added from java)");
            pref.setColorMode(ColorMode.RGB);
            pref.setIndicatorMode(IndicatorMode.HEX);
            pref.setKey("any_key_you_need");
            getPreferenceScreen().addPreference(pref);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            //Sadly, everything is not that easy on API < 11 :(
            //you have to inject SupportFragmentManager manually to each ChromaPreferenceCompat:

            for(int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
                Preference p = getPreferenceScreen().getPreference(i);
                if(p instanceof ChromaPreferenceCompat) {
                    ((ChromaPreferenceCompat) p).setSupportFragmentManager(getFragmentManager());
                }
            }
        }
    }
}

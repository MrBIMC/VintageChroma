package com.pavelsikun.vintagechroma.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;

import com.pavelsikun.vintagechroma.ChromaPreferenceCompat;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

/**
 * Created by Pavel Sikun on 28.05.16.
 */

public class PreferencesCompatActivity extends AppCompatActivity {

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
            addPreferencesFromResource(R.xml.prefs_v7); // load your ChromaPreferenceCompat prefs from xml

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

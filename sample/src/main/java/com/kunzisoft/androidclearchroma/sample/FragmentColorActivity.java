package com.kunzisoft.androidclearchroma.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kunzisoft.androidclearchroma.IndicatorMode;
import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment;

/**
 * Created by joker on 05/02/17.
 */

public class FragmentColorActivity extends AppCompatActivity {

    private static final String TAG_COLOR_FRAGMENT = "TAG_COLOR_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_view);

        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        ChromaColorFragment chromaColorFragment =
                ChromaColorFragment.newInstance(Color.BLUE, ColorMode.ARGB, IndicatorMode.HEX);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_color_fragment, chromaColorFragment, TAG_COLOR_FRAGMENT)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}

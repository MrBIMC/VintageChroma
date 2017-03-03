package com.kunzisoft.androidclearchroma.sample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kunzisoft.androidclearchroma.ChromaUtil;
import com.kunzisoft.androidclearchroma.IndicatorMode;
import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment;
import com.kunzisoft.androidclearchroma.listener.OnColorChangedListener;

/**
 * Created by joker on 05/02/17.
 */

public class FragmentColorActivity extends AppCompatActivity implements OnColorChangedListener {

    private static final String TAG_COLOR_FRAGMENT = "TAG_COLOR_FRAGMENT";

    private static final String SAVED_COLOR = "SAVED_COLOR";
    private @ColorInt int initialColor = Color.BLUE;

    private ActionBar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_view);

        toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            initialColor = savedInstanceState.getInt(SAVED_COLOR, initialColor);
        }

        ChromaColorFragment chromaColorFragment =
                (ChromaColorFragment) getSupportFragmentManager().findFragmentByTag(TAG_COLOR_FRAGMENT);

        if(chromaColorFragment == null)
            chromaColorFragment =
                    ChromaColorFragment.newInstance(initialColor, ColorMode.ARGB, IndicatorMode.HEX);

        chromaColorFragment.setOnColorChangedListener(this);

        onColorChanged(initialColor);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_COLOR, initialColor);
    }

    @Override
    public void onColorChanged(@ColorInt int color) {
        initialColor = color;
        toolbar.setBackgroundDrawable(new ColorDrawable(color));
        toolbar.setTitle(ChromaUtil.getFormattedColorString(color, false));
    }
}

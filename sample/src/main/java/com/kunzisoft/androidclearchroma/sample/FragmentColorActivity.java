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

    private ActionBar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_view);

        toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        if (null == savedInstanceState) {
            @ColorInt int initialColor = Color.BLUE;
            ChromaColorFragment chromaColorFragment =
                    ChromaColorFragment.newInstance(initialColor, ColorMode.ARGB, IndicatorMode.HEX);
            onColorChanged(initialColor);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_color_fragment, chromaColorFragment, TAG_COLOR_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorChanged(@ColorInt int color) {
        toolbar.setBackgroundDrawable(new ColorDrawable(color));
        toolbar.setTitle(ChromaUtil.getFormattedColorString(color, false));
    }
}

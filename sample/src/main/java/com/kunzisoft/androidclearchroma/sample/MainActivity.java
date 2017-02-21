package com.kunzisoft.androidclearchroma.sample;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.kunzisoft.androidclearchroma.ChromaDialog;
import com.kunzisoft.androidclearchroma.ChromaUtil;
import com.kunzisoft.androidclearchroma.IndicatorMode;
import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.listener.OnColorChangedListener;
import com.kunzisoft.androidclearchroma.listener.OnColorSelectedListener;

public class MainActivity extends AppCompatActivity implements OnColorSelectedListener, OnColorChangedListener {

    private static final String EXTRA_COLOR = "EXTRA_COLOR";
    private static final String EXTRA_COLOR_MODE = "EXTRA_COLOR_MODE";
    private static final String EXTRA_INDICATOR_MODE = "EXTRA_INDICATOR_MODE";

    private Spinner spinner;
    private TextView textView;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private @ColorInt int color;
    private ColorMode colorMode;
    private IndicatorMode indicatorMode;

    private static final String TAG_CHROMA_DIALOG = "TAG_CHROMA_DIALOG";
    private ChromaDialog chromaDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView) findViewById(R.id.text_view);
        spinner = (Spinner) findViewById(R.id.spinner);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        if(savedInstanceState == null) {
            color = ContextCompat.getColor(this, R.color.colorPrimary);
            colorMode = ColorMode.RGB;
            indicatorMode = IndicatorMode.HEX;

        } else {
            color = savedInstanceState.getInt(EXTRA_COLOR);
            colorMode = ColorMode.getColorModeFromId(savedInstanceState.getInt(EXTRA_COLOR_MODE));
            indicatorMode = IndicatorMode.getIndicatorModeFromId(savedInstanceState.getInt(EXTRA_INDICATOR_MODE));
            chromaDialog = (ChromaDialog) getSupportFragmentManager().findFragmentByTag(TAG_CHROMA_DIALOG);
            if(chromaDialog != null)
                chromaDialog.setOnColorSelectedListener(this);

        }

        setSupportActionBar(toolbar);
        updateTextView(color);
        updateToolbar(color, color);
        fab.setBackgroundTintList(ColorStateList.valueOf(color));
        setupSpinner();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darkenColor(color));
        }

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chromaDialog == null) {
                    chromaDialog = new ChromaDialog.Builder()
                            .initialColor(color)
                            .colorMode(colorMode)
                            .indicatorMode(indicatorMode) //HEX or DECIMAL;
                            .setOnColorSelectedListener(MainActivity.this)
                            .setOnColorChangedListener(MainActivity.this)
                            .create();
                }
                chromaDialog.show(getSupportFragmentManager(), TAG_CHROMA_DIALOG);
            }
        });

        findViewById(R.id.buttonOpenView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FragmentColorActivity.class));
            }
        });

        findViewById(R.id.prefsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PreferencesCompatActivity.class));
            }
        });
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        for(ColorMode m : ColorMode.values()) {
            adapter.add(m.name());
        }
        adapter.notifyDataSetChanged();

        spinner.setAdapter(adapter);
        spinner.setSelection(colorMode.ordinal());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorMode = ColorMode.values()[position];
                chromaDialog = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void updateTextView(int newColor) {
        textView.setText(ChromaUtil.getFormattedColorString(newColor, colorMode == ColorMode.ARGB));
        textView.setTextColor(newColor);
    }

    private void updateToolbar(int oldColor, int newColor) {
        final TransitionDrawable transition = new TransitionDrawable(new ColorDrawable[]{
                new ColorDrawable(oldColor), new ColorDrawable(newColor)
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            toolbar.setBackground(transition);
        }
        else {
            toolbar.setBackgroundDrawable(transition);
        }

        transition.startTransition(300);
    }

    private int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_COLOR, color);
        outState.putInt(EXTRA_COLOR_MODE, colorMode.ordinal());
        outState.putInt(EXTRA_INDICATOR_MODE, indicatorMode.ordinal());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPositiveButtonClick(@ColorInt int newColor) {
        color = newColor;
        chromaDialog = null;
        fab.setBackgroundTintList(ColorStateList.valueOf(color));
        updateTextView(newColor);
        updateToolbar(color, newColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darkenColor(newColor));
        }
    }

    @Override
    public void onNegativeButtonClick(@ColorInt int color) {}

    @Override
    public void onColorChanged(@ColorInt int color) {}
}

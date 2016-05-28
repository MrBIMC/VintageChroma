package com.pavelsikun.vintagechroma.sample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.ChromaUtil;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_COLOR = "extra_color";
    private static final String EXTRA_MODE = "extra_MODE";

    private Spinner spinner;
    private TextView textView;
    private Toolbar toolbar;

    private int color;
    private ColorMode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView) findViewById(R.id.text_view);
        spinner = (Spinner) findViewById(R.id.spinner);

        color = savedInstanceState == null
                ? ContextCompat.getColor(this, R.color.colorPrimary)
                : savedInstanceState.getInt(EXTRA_COLOR);
        mode = savedInstanceState == null
                ? ColorMode.RGB
                : ColorMode.values()[savedInstanceState.getInt(EXTRA_MODE)];


        setSupportActionBar(toolbar);
        updateTextView(color);
        updateToolbar(color, color);
        setupSpinner();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darkenColor(color));
        }

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog();
            }
        });

        if(Build.VERSION.SDK_INT < 11) {
            findViewById(R.id.prefsCard).setVisibility(View.GONE);
        }

        findViewById(R.id.prefsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
            }
        });

        findViewById(R.id.v7prefsButton).setOnClickListener(new View.OnClickListener() {
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
        spinner.setSelection(mode.ordinal());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = ColorMode.values()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void updateTextView(int newColor) {
        textView.setText(ChromaUtil.getFormattedColorString(newColor, mode == ColorMode.ARGB));
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

    private void showColorPickerDialog() {
        IndicatorMode indicatorMode = IndicatorMode.HEX;
        if(mode == ColorMode.HSV
                || mode == ColorMode.CMYK
                || mode == ColorMode.HSL) indicatorMode = IndicatorMode.DECIMAL; // cuz HEX is dumb for those

        new ChromaDialog.Builder()
            .initialColor(color)
            .colorMode(mode)
            .indicatorMode(indicatorMode) //HEX or DECIMAL;
            .onColorSelected(new OnColorSelectedListener() {
                @Override public void onColorSelected(int newColor) {
                    updateTextView(newColor);
                    updateToolbar(color, newColor);
                    color = newColor;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        getWindow().setStatusBarColor(darkenColor(newColor));
                    }
                }
            })
            .create()
            .show(getSupportFragmentManager(), "dialog");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_COLOR, color);
        outState.putInt(EXTRA_MODE, mode.ordinal());
        super.onSaveInstanceState(outState);
    }
}

package com.pavelsikun.vintagechroma.sample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
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
import com.pavelsikun.vintagechroma.ColorSelectListener;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_COLOR = "extra_color";
    private static final String EXTRA_MODE = "extra_MODE";

    private Spinner spinner;
    private TextView textView;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private int color;
    private int mode = 0;

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
        }
        else {
            color = savedInstanceState.getInt(EXTRA_COLOR);
            mode = savedInstanceState.getInt(EXTRA_MODE);
        }

        setSupportActionBar(toolbar);

        updateTextView(color);
        updateToolbar(color, color);

        setupSpinner();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(darkenColor(color));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog();
            }
        });
    }

    void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        for(ColorMode m : ColorMode.values()) {
            adapter.add(m.name());
        }
        adapter.notifyDataSetChanged();

        spinner.setAdapter(adapter);
        spinner.setSelection(mode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    void updateTextView(int newColor) {
        String formattedColor = String.format("#%06X", 0xFFFFFF & newColor);
        textView.setText(formattedColor);
        textView.setTextColor(newColor);
    }

    void updateToolbar(int oldColor, int newColor) {
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

    int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    private void showColorPickerDialog() {

        new ChromaDialog.Builder()
            .initialColor(color)
            .colorMode(ColorMode.fromOrdinal(mode))
            .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that ColorMode.HSV and IndicatorMode.HEX is a bad idea
            .onColorSelected(new ColorSelectListener() {
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
            .show(getFragmentManager(), "dialog");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_COLOR, color);
        outState.putInt(EXTRA_MODE, mode);
        super.onSaveInstanceState(outState);
    }
}

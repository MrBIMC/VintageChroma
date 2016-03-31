package com.pavelsikun.vintagechroma;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.preference.Preference;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


import com.pavelsikun.vintagechroma.colormode.ColorMode;

/**
 * Created by Pavel Sikun on 31.03.16.
 */
public class ChromaPreference extends Preference implements View.OnClickListener, OnColorSelectedListener {

    private ImageView colorPreview;

    private static int DEFAULT_COLOR = Color.WHITE;
    private static ColorMode DEFAULT_COLOR_MODE = ColorMode.RGB;
    private static IndicatorMode DEFAULT_INDICATOR_MODE = IndicatorMode.DECIMAL;

    private int color;
    private ColorMode colorMode;
    private IndicatorMode indicatorMode;

    private OnColorSelectedListener listener;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChromaPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public ChromaPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ChromaPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ChromaPreference(Context context) {
        super(context);
        init(null);
    }


    void init(AttributeSet attrs) {
        setWidgetLayoutResource(R.layout.preference_layout);
        loadValuesFromXml(attrs);
        updatePreview();
    }

    void loadValuesFromXml(AttributeSet attrs) {
        if(attrs == null) {
            color = DEFAULT_COLOR;
            colorMode = DEFAULT_COLOR_MODE;
            indicatorMode = DEFAULT_INDICATOR_MODE;
        }
        else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ChromaPreference);
            try {
                color = a.getColor(R.styleable.ChromaPreference_chromaInitialColor, DEFAULT_COLOR);

                colorMode = ColorMode.values()[
                        a.getInt(R.styleable.ChromaPreference_chromaColorMode,
                                DEFAULT_COLOR_MODE.ordinal())];

                indicatorMode = IndicatorMode.values()[
                        a.getInt(R.styleable.ChromaPreference_chromaIndicatorMode,
                                DEFAULT_INDICATOR_MODE.ordinal())];
            }
            finally {
                a.recycle();
            }
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
        color = getPersistedInt(color);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        view.setOnClickListener(this);

        colorPreview = (ImageView) view.findViewById(R.id.colorPreview);
        updatePreview();

        if(!isEnabled()) {
            colorPreview.getDrawable().mutate().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        }
    }

    void updatePreview() {
        if(colorPreview != null) {
            colorPreview
                    .getDrawable()
                    .mutate()
                    .setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }

        setSummary(ChromaUtil.getFormattedColorString(color, colorMode == ColorMode.ARGB));
    }

    @Override
    public void onClick(View v) {
        new ChromaDialog.Builder()
                .colorMode(colorMode)
                .initialColor(color)
                .onColorSelected(this)
                .indicatorMode(indicatorMode)
                .create().show(((Activity) getContext()).getFragmentManager(), "colorPicker");
    }

    @Override
    protected boolean persistInt(int value) {
        color = value;
        updatePreview();
        return super.persistInt(value);
    }

    @Override
    public void onColorSelected(@ColorInt int color) {
        persistInt(color);
        listener.onColorSelected(color);
    }

    public int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        persistInt(color);
    }

    public void setOnColorSelectedListener(OnColorSelectedListener listener) {
        this.listener = listener;
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public void setColorMode(ColorMode colorMode) {
        this.colorMode = colorMode;
    }

    public IndicatorMode getIndicatorMode() {
        return indicatorMode;
    }

    public void setIndicatorMode(IndicatorMode indicatorMode) {
        this.indicatorMode = indicatorMode;
    }
}

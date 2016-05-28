package com.pavelsikun.vintagechroma;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.support.v7.preference.Preference;
import android.util.Log;
import android.widget.ImageView;

import com.pavelsikun.vintagechroma.colormode.ColorMode;

/**
 * Created by Pavel Sikun on 5.04.16.
 */
public class ChromaPreferenceCompat extends Preference implements OnColorSelectedListener {

    private ImageView colorPreview;

    private static final int DEFAULT_COLOR = Color.WHITE;
    private static final ColorMode DEFAULT_COLOR_MODE = ColorMode.RGB;
    private static final IndicatorMode DEFAULT_INDICATOR_MODE = IndicatorMode.DECIMAL;

    private int color;
    private ColorMode colorMode;
    private IndicatorMode indicatorMode;

    private OnColorSelectedListener listener;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChromaPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public ChromaPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ChromaPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ChromaPreferenceCompat(Context context) {
        super(context);
        init(null);
    }


    private void init(AttributeSet attrs) {
        setWidgetLayoutResource(R.layout.preference_layout);
        loadValuesFromXml(attrs);
        updatePreview();
    }

    private void loadValuesFromXml(AttributeSet attrs) {
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
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        colorPreview = (ImageView) holder.itemView.findViewById(R.id.colorPreview);
        updatePreview();

        if(!isEnabled()) {
            colorPreview.getDrawable().mutate().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override
    public void onAttached() {
        super.onAttached();
        updatePreview();
    }

    void updatePreview() {
        try {
            if(colorPreview != null) {
                colorPreview
                        .getDrawable()
                        .mutate()
                        .setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }

            setSummary(ChromaUtil.getFormattedColorString(color, colorMode == ColorMode.ARGB));
        }
        catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Cannot update preview: " + e.toString());
        }
    }

    @Override
    protected void onClick() {
        super.onClick();
        new ChromaDialog.Builder()
                .colorMode(colorMode)
                .initialColor(color)
                .onColorSelected(this)
                .indicatorMode(indicatorMode)
                .createCompat(getContext());
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

        if(listener != null) {
            listener.onColorSelected(color);
        }
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

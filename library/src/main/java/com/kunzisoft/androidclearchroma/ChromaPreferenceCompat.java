package com.kunzisoft.androidclearchroma;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.kunzisoft.androidclearchroma.colormode.ColorMode;

/**
 * Created by Pavel Sikun on 5.04.16.
 * Modified by Jeremy JAMET on 12/09/16.
 */
public class ChromaPreferenceCompat extends DialogPreference {

    private static final String TAG = "ChromaPreferenceCompat";

    private ImageView colorPreview;

    private static final int DEFAULT_COLOR = Color.WHITE;
    private static final ColorMode DEFAULT_COLOR_MODE = ColorMode.RGB;
    private static final IndicatorMode DEFAULT_INDICATOR_MODE = IndicatorMode.DECIMAL;
    private static final ShapePreviewPreference DEFAULT_SHAPE_PREVIEW = ShapePreviewPreference.CIRCLE;
    private static final String COLOR_TAG_SUMMARY = "[color]";

    private int color;
    private ColorMode colorMode;
    private IndicatorMode indicatorMode;
    private ShapePreviewPreference shapePreviewPreference;
    private CharSequence summaryPreference;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChromaPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public ChromaPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ChromaPreferenceCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChromaPreferenceCompat(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        setWidgetLayoutResource(R.layout.preference_layout);
        loadValuesFromXml(attrs);
        updatePreview();
    }

    private void loadValuesFromXml(AttributeSet attrs) {
        if(attrs == null) {
            color = DEFAULT_COLOR;
            colorMode = DEFAULT_COLOR_MODE;
            indicatorMode = DEFAULT_INDICATOR_MODE;
            shapePreviewPreference = DEFAULT_SHAPE_PREVIEW;
            summaryPreference = COLOR_TAG_SUMMARY;
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

                shapePreviewPreference = ShapePreviewPreference.values()[
                        a.getInt(R.styleable.ChromaPreference_chromaShapePreview,
                                DEFAULT_SHAPE_PREVIEW.ordinal())];

                summaryPreference = getSummary();
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

    synchronized private void updatePreview() {
        try {
            if(colorPreview != null) {
                // Update shape of color preview
                switch (shapePreviewPreference) {
                    case CIRCLE:
                        colorPreview.setImageResource(R.drawable.circle);
                        break;
                    case SQUARE:
                        colorPreview.setImageResource(R.drawable.square);
                        break;
                    case ROUNDED_SQUARE:
                        colorPreview.setImageResource(R.drawable.rounded_square);
                        break;
                }
                // Update color
                colorPreview
                        .getDrawable()
                        .mutate()
                        .setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }
            setSummary(summaryPreference);
        }
        catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Cannot update preview: " + e.toString());
        }
    }

    @Override
    protected void onClick() {
        getPreferenceManager().showDialog(this);
    }

    @Override
    protected boolean persistInt(int value) {
        color = value;
        updatePreview();
        return super.persistInt(value);
    }

    @Override
    /**
     * If [color] is present in summary, it's replaced by string of color
     */
    public void setSummary(CharSequence summary) {
        String summaryWithColor = null;
        if(summary != null) {
            summaryWithColor = summary.toString().replace(COLOR_TAG_SUMMARY,
                    ChromaUtil.getFormattedColorString(color, colorMode == ColorMode.ARGB));
        }
        super.setSummary(summaryWithColor);
    }

    public int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        persistInt(color);
        notifyChanged();
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public void setColorMode(ColorMode colorMode) {
        this.colorMode = colorMode;
        notifyChanged();
    }

    public IndicatorMode getIndicatorMode() {
        return indicatorMode;
    }

    public void setIndicatorMode(IndicatorMode indicatorMode) {
        this.indicatorMode = indicatorMode;
        notifyChanged();
    }

    public ShapePreviewPreference getShapePreviewPreference() {
        return shapePreviewPreference;
    }

    public void setShapePreviewPreference(ShapePreviewPreference shapePreviewPreference) {
        this.shapePreviewPreference = shapePreviewPreference;
        notifyChanged();
    }
}

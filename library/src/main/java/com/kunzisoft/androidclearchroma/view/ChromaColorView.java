package com.kunzisoft.androidclearchroma.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kunzisoft.androidclearchroma.IndicatorMode;
import com.kunzisoft.androidclearchroma.R;
import com.kunzisoft.androidclearchroma.colormode.Channel;
import com.kunzisoft.androidclearchroma.colormode.ColorMode;

import java.util.ArrayList;
import java.util.List;

/**
 * View for select color with many seekbar
 *
 * Created by Pavel Sikun on 28.03.16.
 * Modified by Jeremy Jamet on 5.02.17
 */
public class ChromaColorView extends RelativeLayout {

    public final static int DEFAULT_COLOR = Color.GRAY;
    public final static ColorMode DEFAULT_MODE = ColorMode.RGB;
    public final static IndicatorMode DEFAULT_INDICATOR = IndicatorMode.DECIMAL;

    private @ColorInt int currentColor;
    private ColorMode colorMode;
    private IndicatorMode indicatorMode;

    private AppCompatImageView colorView;

    public ChromaColorView(Context context) {
        super(context);
        init();
    }

    public ChromaColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ChromaColorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public ChromaColorView(Context context, @ColorInt int initialColor, ColorMode colorMode, IndicatorMode indicatorMode) {
        super(context);
        this.indicatorMode = indicatorMode;
        this.colorMode = colorMode;
        this.currentColor = initialColor;
        init();
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ChromaPreference,
                0, 0);
        try {
            currentColor = a.getInt(R.styleable.ChromaPreference_chromaInitialColor, DEFAULT_COLOR);
            colorMode = ColorMode.getColorModeFromId(a.getInt(R.styleable.ChromaPreference_chromaColorMode, DEFAULT_MODE.getId()));
            indicatorMode = IndicatorMode.getIndicatorModeFromId(a.getInt(R.styleable.ChromaPreference_chromaIndicatorMode, DEFAULT_INDICATOR.getId()));
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.chroma_view, this);
        setClipToPadding(false);

        colorView = (AppCompatImageView) findViewById(R.id.color_view);
        Drawable colorViewDrawable = new ColorDrawable(currentColor);
        colorView.setImageDrawable(colorViewDrawable);

        List<Channel> channels = colorMode.getColorMode().getChannels();
        final List<ChannelView> channelViews = new ArrayList<>();
        for(Channel channel : channels) {
            channelViews.add(new ChannelView(channel, currentColor, indicatorMode, getContext()));
        }

        ChannelView.OnProgressChangedListener seekBarChangeListener = new ChannelView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged() {
                List<Channel> channels = new ArrayList<>();
                for(ChannelView chan : channelViews) {
                    channels.add(chan.getChannel());
                }
                currentColor = colorMode.getColorMode().evaluateColor(channels);
                Drawable colorViewDrawable = new ColorDrawable(currentColor);
                colorView.setImageDrawable(colorViewDrawable);
            }
        };

        ViewGroup channelContainer = (ViewGroup) findViewById(R.id.channel_container);
        for(ChannelView c : channelViews) {
            channelContainer.addView(c);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) c.getLayoutParams();
            params.topMargin =
                    getResources().getDimensionPixelSize(R.dimen.channel_view_margin_top);
            params.bottomMargin =
                    getResources().getDimensionPixelSize(R.dimen.channel_view_margin_bottom);

            c.registerListener(seekBarChangeListener);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRGB(200, 200, 200);
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public IndicatorMode getIndicatorMode() {
        return indicatorMode;
    }

    public void enableButtonBar(final ButtonBarListener listener) {
        LinearLayout buttonBar = (LinearLayout) findViewById(R.id.button_bar);
        Button positiveButton = (Button) buttonBar.findViewById(R.id.positive_button);
        Button negativeButton = (Button) buttonBar.findViewById(R.id.negative_button);

        if(listener != null) {
            buttonBar.setVisibility(VISIBLE);
            positiveButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPositiveButtonClick(currentColor);
                }
            });

            negativeButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNegativeButtonClick();
                }
            });
        }
        else {
            buttonBar.setVisibility(GONE);
            positiveButton.setOnClickListener(null);
            negativeButton.setOnClickListener(null);
        }
    }

    public interface ButtonBarListener {
        void onPositiveButtonClick(int color);
        void onNegativeButtonClick();
    }
}

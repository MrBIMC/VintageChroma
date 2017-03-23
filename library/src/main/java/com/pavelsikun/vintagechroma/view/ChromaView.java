package com.pavelsikun.vintagechroma.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.R;
import com.pavelsikun.vintagechroma.colormode.Channel;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel Sikun on 28.03.16.
 */
public class ChromaView extends RelativeLayout {

    public final static int DEFAULT_COLOR = Color.GRAY;
    public final static ColorMode DEFAULT_MODE = ColorMode.RGB;
    public final static IndicatorMode DEFAULT_INDICATOR = IndicatorMode.DECIMAL;

    private final ColorMode colorMode;
    private @ColorInt int currentColor;
    private IndicatorMode indicatorMode;

    public ChromaView(Context context) {
        this(DEFAULT_COLOR, DEFAULT_MODE, DEFAULT_INDICATOR, context);
    }

    public ChromaView(@ColorInt int initialColor, ColorMode colorMode, Context context) {
        this(initialColor, colorMode, DEFAULT_INDICATOR, context);
    }

    public ChromaView(@ColorInt int initialColor, ColorMode colorMode, IndicatorMode indicatorMode, Context context) {
        super(context);
        this.indicatorMode = indicatorMode;
        this.colorMode = colorMode;
        this.currentColor = initialColor;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.chroma_view, this);
        setClipToPadding(false);

        final View colorView = findViewById(R.id.color_view);
        colorView.setBackgroundColor(currentColor);

        List<Channel> channels = colorMode.getColorMode().getChannels();
        final List<ChannelView> channelViews = new ArrayList<>();
        for(Channel c : channels) {
            channelViews.add(new ChannelView(c, currentColor, indicatorMode, getContext()));
        }

        ChannelView.OnProgressChangedListener seekBarChangeListener = new ChannelView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged() {
                List<Channel> channels = new ArrayList<>();
                for(ChannelView chan : channelViews) {
                    channels.add(chan.getChannel());
                }
                currentColor = colorMode.getColorMode().evaluateColor(channels);
                colorView.setBackgroundColor(currentColor);
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

    public ColorMode getColorMode() {
        return colorMode;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public IndicatorMode getIndicatorMode() {
        return indicatorMode;
    }

    public interface ButtonBarListener {
        void onPositiveButtonClick(int color);
        void onNegativeButtonClick();
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
}

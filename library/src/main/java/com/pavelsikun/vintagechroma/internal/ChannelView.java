package com.pavelsikun.vintagechroma.internal;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pavelsikun.vintagechroma.R;
import com.pavelsikun.vintagechroma.colormode.Channel;

/**
 * Created by REDACTED on 28.03.16.
 */
public class ChannelView extends RelativeLayout {

    private final Channel channel;
    private @ColorInt int color;
    private Context context;

    private OnProgressChangedListener listener;

    public interface OnProgressChangedListener {
        void onProgressChanged();
    }

    public ChannelView(Channel channel, int color, Context context) {
        super(context);
        this.channel = channel;
        this.color = color;
        this.context = context;

        channel.setProgress(channel.getExtractor().extract(color));

        if(channel.getProgress() < channel.getMin() || channel.getProgress() > channel.getMax()) {
            throw new IllegalArgumentException(
                    "Initial progress for channel: " + channel.getClass().getSimpleName()
                            + " must be between " + channel.getMin() + " and " + channel.getMax());
        }

            View rootView = inflate(context, R.layout.channel_row, this);
            bindViews(rootView);
        }

    private void bindViews(View rootView) {
        TextView label = (TextView) rootView.findViewById(R.id.label);
        label.setText(context.getString(channel.getNameResourceId()));

        final TextView progressView = (TextView) rootView.findViewById(R.id.progress_text);
        progressView.setText(String.valueOf(channel.getProgress()));

        SeekBar seekbar = (SeekBar) rootView.findViewById(R.id.seekbar);
        seekbar.setMax(channel.getMax());
        seekbar.setProgress(channel.getProgress());

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                channel.setProgress(progress);
                progressView.setText(String.valueOf(progress));
                if(listener != null) listener.onProgressChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void registerListener(OnProgressChangedListener listener) {
        this.listener = listener;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        listener = null;
    }
}

package com.pavelsikun.vintagechroma.colormode.mode;

import android.graphics.Color;

import com.pavelsikun.vintagechroma.R;
import com.pavelsikun.vintagechroma.colormode.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel Sikun on 01.04.16.
 */
public class CMYK implements AbstractColorMode {

    @Override
    public List<Channel> getChannels() {
        List<Channel> list = new ArrayList<>();

        list.add(new Channel(R.string.channel_cyan, 0, 100, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return Color.red(color);
            }
        }));

        list.add(new Channel(R.string.channel_magenta, 0, 100, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return Color.green(color);
            }
        }));

        list.add(new Channel(R.string.channel_yellow, 0, 100, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return Color.blue(color);
            }
        }));

        list.add(new Channel(R.string.channel_black, 0, 100, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return Color.alpha(color);
            }
        }));

        return list;
    }

    @Override
    public int evaluateColor(List<Channel> channels) {
        return Color.rgb(
                convertToRGB(channels.get(0), channels.get(3)),
                convertToRGB(channels.get(1), channels.get(3)),
                convertToRGB(channels.get(2), channels.get(3)));
    }

    private int convertToRGB(Channel colorChan, Channel blackChan) {
        return (int) ((255 - (double) colorChan.getProgress() * 2.55) *
                        (255 - (double) blackChan.getProgress() * 2.55)) / 255;
    }
}

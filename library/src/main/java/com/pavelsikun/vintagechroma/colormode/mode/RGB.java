package com.pavelsikun.vintagechroma.colormode.mode;

import android.graphics.Color;

import com.pavelsikun.vintagechroma.R;
import com.pavelsikun.vintagechroma.colormode.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel Sikun on 28.03.16.
 */
public class RGB implements AbstractColorMode {


    @Override
    public List<Channel> getChannels() {
        List<Channel> list = new ArrayList<>();

        list.add(new Channel(R.string.channel_red, 0, 255, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return Color.red(color);
            }
        }));

        list.add(new Channel(R.string.channel_green, 0, 255, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return Color.green(color);
            }
        }));

        list.add(new Channel(R.string.channel_blue, 0, 255, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return Color.blue(color);
            }
        }));

        return list;
    }

    @Override
    public int evaluateColor(List<Channel> channels) {
        return Color.rgb(
                channels.get(0).getProgress(),
                channels.get(1).getProgress(),
                channels.get(2).getProgress());
    }
}

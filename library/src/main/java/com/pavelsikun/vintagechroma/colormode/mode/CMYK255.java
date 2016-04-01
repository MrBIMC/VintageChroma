package com.pavelsikun.vintagechroma.colormode.mode;

import android.graphics.Color;

import com.pavelsikun.vintagechroma.R;
import com.pavelsikun.vintagechroma.colormode.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel Sikun on 01.04.16.
 */
public class CMYK255 implements AbstractColorMode {

    @Override
    public List<Channel> getChannels() {
        List<Channel> list = new ArrayList<>();

        list.add(new Channel(R.string.channel_cyan, 0, 255, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return 255 - Color.red(color);
            }
        }));

        list.add(new Channel(R.string.channel_magenta, 0, 255, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return 255 - Color.green(color);
            }
        }));

        list.add(new Channel(R.string.channel_yellow, 0, 255, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return 255 -  Color.blue(color);
            }
        }));

        list.add(new Channel(R.string.channel_black, 0, 255, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return 255 - Color.alpha(color);
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
        return ((255 - colorChan.getProgress()) * (255 - blackChan.getProgress())) / 255;
    }
}

package com.pavelsikun.vintagechroma.colormode.mode;

import android.graphics.Color;

import com.pavelsikun.vintagechroma.R;
import com.pavelsikun.vintagechroma.colormode.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel Sikun on 28.03.16.
 */
public class CMYK implements AbstractColorMode {

    @Override
    public List<Channel> getChannels() {
        List<Channel> list = new ArrayList<>();

        list.add(new Channel(R.string.channel_cyan, 0, 100, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return 100 - (int) (Color.red((int) (color * 2.55)) / 2.55);
            }
        }));

        list.add(new Channel(R.string.channel_magenta, 0, 100, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return 100 - (int) (Color.green((int) (color * 2.55)) / 2.55);
            }
        }));

        list.add(new Channel(R.string.channel_yellow, 0, 100, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return 100 - (int) (Color.blue((int) (color * 2.55)) / 2.55);
            }
        }));

        list.add(new Channel(R.string.channel_black, 0, 100, new Channel.ColorExtractor() {
            @Override
            public int extract(int color) {
                return 100 - (int) (Color.alpha((int) (color * 2.55)) / 2.55);
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
        return (int)((255 - colorChan.getProgress() * 2.55) * (255 - blackChan.getProgress() * 2.55)) / 255;
    }
}

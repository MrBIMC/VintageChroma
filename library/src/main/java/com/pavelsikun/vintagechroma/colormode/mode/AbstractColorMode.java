package com.pavelsikun.vintagechroma.colormode.mode;

import com.pavelsikun.vintagechroma.colormode.Channel;

import java.util.List;

/**
 * Created by Pavel Sikun on 28.03.16.
 */
public interface AbstractColorMode {
    int evaluateColor(List<Channel> channels);
    List<Channel> getChannels();
}

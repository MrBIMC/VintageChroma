package com.pavelsikun.vintagechroma.colormode.mode;

import com.pavelsikun.vintagechroma.colormode.Channel;

import java.util.List;

/**
 * Created by REDACTED on 28.03.16.
 */
public interface AbstractColorMode {
    abstract int evaluateColor(List<Channel> channels);
    abstract List<Channel> getChannels();
}

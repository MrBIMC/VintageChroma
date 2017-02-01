package com.kunzisoft.androidclearchroma.colormode.mode;

import com.kunzisoft.androidclearchroma.colormode.Channel;

import java.util.List;

/**
 * Created by Pavel Sikun on 28.03.16.
 */
public interface AbstractColorMode {
    int evaluateColor(List<Channel> channels);
    List<Channel> getChannels();
}

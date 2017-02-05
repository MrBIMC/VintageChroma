package com.kunzisoft.androidclearchroma;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;

import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.view.ChromaView;

/**
 * Created by Pavel Sikun on 28.05.16.
 */

class ChromaDialogCompat {

    private @ColorInt int initialColor = Color.GRAY;
    private ColorMode colorMode = ColorMode.RGB;
    private IndicatorMode indicatorMode = IndicatorMode.DECIMAL;
    private OnColorSelectedListener listener = null;

    ChromaDialogCompat(Context context, int initialColor, ColorMode colorMode, IndicatorMode indicatorMode, OnColorSelectedListener listener) {
        this.indicatorMode = indicatorMode;
        this.colorMode = colorMode;
        this.initialColor = initialColor;
        this.listener = listener;

        init(new AlertDialog.Builder(context, R.style.Chroma_Dialog_Default));
    }

    private void init(AlertDialog.Builder dialogBuilder) {
        ChromaView chromaView = new ChromaView(initialColor, colorMode, indicatorMode, dialogBuilder.getContext());
        final Dialog dialog = dialogBuilder.setView(chromaView).create();
        chromaView.enableButtonBar(new ChromaView.ButtonBarListener() {
            @Override
            public void onPositiveButtonClick(int color) {
                if(listener != null) {
                    listener.onColorSelected(color);
                }
                dialog.dismiss();
            }

            @Override
            public void onNegativeButtonClick() {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

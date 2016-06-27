package com.pavelsikun.vintagechroma;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;

import com.pavelsikun.vintagechroma.colormode.ColorMode;
import com.pavelsikun.vintagechroma.view.ChromaView;

/**
 * Created by Pavel Sikun on 28.05.16.
 */

class ChromaDialogCompat {

    private @ColorInt int initialColor = ChromaView.DEFAULT_COLOR;
    private ColorMode colorMode = ChromaView.DEFAULT_MODE;
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
        ChromaView dialogView = new ChromaView(initialColor, colorMode, indicatorMode, dialogBuilder.getContext());
        final Dialog dialog = dialogBuilder.setView(dialogView).create();

        dialogView.enableButtonBar(new ChromaView.ButtonBarListener() {
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

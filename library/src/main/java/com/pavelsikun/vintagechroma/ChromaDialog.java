package com.pavelsikun.vintagechroma;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.pavelsikun.vintagechroma.colormode.ColorMode;
import com.pavelsikun.vintagechroma.internal.ChromaView;

/**
 * Created by REDACTED on 28.03.16.
 */
public class ChromaDialog extends DialogFragment {

    private final static String ARG_INITIAL_COLOR = "arg_initial_color";
    private final static String ARG_COLOR_MODE_ID = "arg_color_mode_id";

    private ColorSelectListener listener;
    private ChromaView chromaView;

    private static ChromaDialog newInstance(@ColorInt int initialColor, ColorMode colorMode) {
        ChromaDialog fragment = new ChromaDialog();
        fragment.setArguments(makeArgs(initialColor, colorMode));
        return fragment;
    }

    private static Bundle makeArgs(@ColorInt int initialColor, ColorMode colorMode) {
        Bundle args = new Bundle();
        args.putInt(ARG_INITIAL_COLOR, initialColor);
        args.putInt(ARG_COLOR_MODE_ID, colorMode.getID());
        return args;
    }

    public static class Builder {
        private @ColorInt int initialColor = ChromaView.DEFAULT_COLOR;
        private ColorMode colorMode = ChromaView.DEFAULT_MODE;
        private ColorSelectListener listener = null;

        public Builder initialColor(@ColorInt int initialColor) {
            this.initialColor = initialColor;
            return this;
        }

        public Builder colorMode(ColorMode colorMode) {
            this.colorMode = colorMode;
            return this;
        }

        public Builder onColorSelected(ColorSelectListener listener) {
            this.listener = listener;
            return this;
        }

        public ChromaDialog create() {
            ChromaDialog fragment = newInstance(initialColor, colorMode);
            fragment.setListener(listener);
            return fragment;
        }
    }

    public void setListener(ColorSelectListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        if(savedInstanceState == null) {
            chromaView = new ChromaView(
                    getArguments().getInt(ARG_INITIAL_COLOR),
                    ColorMode.fromID(getArguments().getInt(ARG_COLOR_MODE_ID, ChromaView.DEFAULT_MODE.getID())),
                    getActivity());
        }
        else {
            chromaView = new ChromaView(
                    savedInstanceState.getInt(ARG_INITIAL_COLOR, ChromaView.DEFAULT_COLOR),
                    ColorMode.fromID(savedInstanceState.getInt(ARG_COLOR_MODE_ID, ChromaView.DEFAULT_MODE.getID())),
                    getActivity());
        }

        chromaView.enableButtonBar(new ChromaView.ButtonBarListener() {
            @Override
            public void onPositiveButtonClick(int color) {
                if(listener != null) listener.onColorSelected(color);
                dismiss();
            }

            @Override
            public void onNegativeButtonClick() {
                dismiss();
            }
        });

        final AlertDialog ad = new AlertDialog.Builder(getActivity(), getTheme()).setView(chromaView).create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                int multiplier = 1;
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    multiplier = 2;
                }

                int width = getResources().getDimensionPixelSize(R.dimen.chroma_dialog_width) * multiplier;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                ad.getWindow().setLayout(width, height);
            }
        });

        return ad;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putAll(makeArgs(chromaView.getCurrentColor(), chromaView.getColorMode()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listener = null;
    }
}

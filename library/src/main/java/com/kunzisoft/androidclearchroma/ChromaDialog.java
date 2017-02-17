package com.kunzisoft.androidclearchroma;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment;

import static com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment.ARG_COLOR_MODE_ID;
import static com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment.ARG_INDICATOR_MODE;
import static com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment.ARG_INITIAL_COLOR;

/**
 * Created by Pavel Sikun on 28.03.16.
 * Modified by Jeremy JAMET on 12/09/16.
 */
public class ChromaDialog extends DialogFragment {

    private final static String TAG = "ChromaDialog";

    private final static int DEFAULT_COLOR = Color.GRAY;
    private final static ColorMode DEFAULT_MODE = ColorMode.RGB;

    private final static String TAG_FRAGMENT_COLORS = "TAG_FRAGMENT_COLORS";

    private OnColorSelectedListener onColorSelectedListener;
    private CallbackButtonListener callbackButtonListener;

    private ChromaColorFragment chromaColorFragment;

    private static ChromaDialog newInstance(@ColorInt int initialColor, ColorMode colorMode, IndicatorMode indicatorMode) {
        ChromaDialog fragment = new ChromaDialog();
        fragment.setArguments(makeArgs(initialColor, colorMode, indicatorMode));
        return fragment;
    }

    private static Bundle makeArgs(@ColorInt int initialColor, ColorMode colorMode, IndicatorMode indicatorMode) {
        Bundle args = new Bundle();
        args.putInt(ARG_INITIAL_COLOR, initialColor);
        args.putInt(ARG_COLOR_MODE_ID, colorMode.ordinal());
        args.putInt(ARG_INDICATOR_MODE, indicatorMode.ordinal());
        return args;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.color_dialog_fragment, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        chromaColorFragment = (ChromaColorFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_COLORS);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(chromaColorFragment == null) {
            chromaColorFragment = ChromaColorFragment.newInstance(getArguments());
            fragmentTransaction.add(R.id.color_dialog_container, chromaColorFragment, TAG_FRAGMENT_COLORS).commit();
        } else {
            //chromaColorFragment.setArguments(getArguments());
        }

        LinearLayout buttonBar = (LinearLayout) root.findViewById(R.id.button_bar);
        Button positiveButton = (Button) buttonBar.findViewById(R.id.positive_button);
        Button negativeButton = (Button) buttonBar.findViewById(R.id.negative_button);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callbackButtonListener != null)
                    callbackButtonListener.onPositiveButtonClick(chromaColorFragment.getCurrentColor());
                dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callbackButtonListener != null)
                    callbackButtonListener.onNegativeButtonClick(chromaColorFragment.getCurrentColor());
                dismiss();
            }
        });

        return root;
    }

    // TODO doc
    public static class Builder {
        private @ColorInt int initialColor = DEFAULT_COLOR;
        private ColorMode colorMode = DEFAULT_MODE;
        private IndicatorMode indicatorMode = IndicatorMode.DECIMAL;
        private OnColorSelectedListener onColorSelectedListener;
        private CallbackButtonListener callbackButtonListener;

        public Builder initialColor(@ColorInt int initialColor) {
            this.initialColor = initialColor;
            return this;
        }

        public Builder colorMode(ColorMode colorMode) {
            this.colorMode = colorMode;
            return this;
        }

        public Builder indicatorMode(IndicatorMode indicatorMode) {
            this.indicatorMode = indicatorMode;
            return this;
        }

        public Builder onColorSelected(OnColorSelectedListener onColorSelectedListener) {
            this.onColorSelectedListener = onColorSelectedListener;
            return this;
        }

        public Builder setCallbackButtonListener(CallbackButtonListener callbackButtonListener) {
            this.callbackButtonListener = callbackButtonListener;
            return this;
        }

        /*
        //TODO
        void createCompat(Context context) {
            new ChromaDialogCompat(context, initialColor, colorMode, indicatorMode, onColorSelectedListener);
        }
        */

        public ChromaDialog create() {
            ChromaDialog chromaDialog = newInstance(initialColor, colorMode, indicatorMode);
            chromaDialog.setOnColorSelectedListener(onColorSelectedListener);
            chromaDialog.setOnCallbackButtonListener(callbackButtonListener);
            return chromaDialog;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
        this.onColorSelectedListener = onColorSelectedListener;
    }

    public void setOnCallbackButtonListener(CallbackButtonListener callbackButtonListener) {
        this.callbackButtonListener = callbackButtonListener;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putAll(makeArgs(chromaColorView.getCurrentColor(), chromaColorView.getColorMode(), chromaColorView.getIndicatorMode()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onColorSelectedListener = null;
    }

    /**
     * TODO
     */
    public interface CallbackButtonListener {
        void onPositiveButtonClick(@ColorInt int color);
        void onNegativeButtonClick(@ColorInt int color);
    }
}

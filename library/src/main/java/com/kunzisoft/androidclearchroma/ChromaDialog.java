package com.kunzisoft.androidclearchroma;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment;
import com.kunzisoft.androidclearchroma.listener.OnColorSelectedListener;

import static com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment.ARG_COLOR_MODE;
import static com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment.ARG_INDICATOR_MODE;
import static com.kunzisoft.androidclearchroma.fragment.ChromaColorFragment.ARG_INITIAL_COLOR;

/**
 * Created by Pavel Sikun on 28.03.16.
 * Modified by Jeremy JAMET on 12/09/16.
 */
public class ChromaDialog extends DialogFragment {

    private final static String TAG = "ChromaDialog";

    private final static String ARG_KEY = "ARG_KEY";

    private final static int DEFAULT_COLOR = Color.GRAY;
    private final static ColorMode DEFAULT_MODE = ColorMode.RGB;

    private final static String TAG_FRAGMENT_COLORS = "TAG_FRAGMENT_COLORS";

    private OnColorSelectedListener onColorSelectedListener;

    private ChromaColorFragment chromaColorFragment;

    public static ChromaDialog newInstance(String key, @ColorInt int initialColor, ColorMode colorMode, IndicatorMode indicatorMode) {
        ChromaDialog fragment = new ChromaDialog();
        Bundle args = makeArgs(initialColor, colorMode, indicatorMode);
        args.putString(ARG_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    private static ChromaDialog newInstance(@ColorInt int initialColor, ColorMode colorMode, IndicatorMode indicatorMode) {
        ChromaDialog fragment = new ChromaDialog();
        fragment.setArguments(makeArgs(initialColor, colorMode, indicatorMode));
        return fragment;
    }

    private static Bundle makeArgs(@ColorInt int initialColor, ColorMode colorMode, IndicatorMode indicatorMode) {
        Bundle args = new Bundle();
        args.putInt(ARG_INITIAL_COLOR, initialColor);
        args.putInt(ARG_COLOR_MODE, colorMode.ordinal());
        args.putInt(ARG_INDICATOR_MODE, indicatorMode.ordinal());
        return args;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.Chroma_AlertDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.color_dialog_fragment, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        chromaColorFragment = (ChromaColorFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_COLORS);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (chromaColorFragment == null) {
            chromaColorFragment = ChromaColorFragment.newInstance(getArguments());
            fragmentTransaction.add(R.id.color_dialog_container, chromaColorFragment, TAG_FRAGMENT_COLORS).commit();
        }

        LinearLayout buttonBar = (LinearLayout) root.findViewById(R.id.button_bar);
        Button positiveButton = (Button) buttonBar.findViewById(R.id.positive_button);
        Button negativeButton = (Button) buttonBar.findViewById(R.id.negative_button);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Activity activity = getActivity();
                final Fragment fragment = getTargetFragment();
                if(onColorSelectedListener != null) {
                    onColorSelectedListener.onPositiveButtonClick(chromaColorFragment.getCurrentColor());
                } else if (activity instanceof OnColorSelectedListener) {
                    ((OnColorSelectedListener) activity).onPositiveButtonClick(chromaColorFragment.getCurrentColor());
                } else if (fragment instanceof OnColorSelectedListener) {
                    ((OnColorSelectedListener) fragment).onPositiveButtonClick(chromaColorFragment.getCurrentColor());
                }
                dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Activity activity = getActivity();
                final Fragment fragment = getTargetFragment();
                if(onColorSelectedListener != null) {
                    onColorSelectedListener.onNegativeButtonClick(chromaColorFragment.getCurrentColor());
                } else if (activity instanceof OnColorSelectedListener) {
                    ((OnColorSelectedListener) activity).onNegativeButtonClick(chromaColorFragment.getCurrentColor());
                } else if (fragment instanceof OnColorSelectedListener) {
                    ((OnColorSelectedListener) fragment).onNegativeButtonClick(chromaColorFragment.getCurrentColor());
                }
                dismiss();
            }
        });

        return root;
    }

    /**
     * Set new dimensions to dialog
     *
     * @param ad
     */
    private void measureLayout(Dialog ad) {
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.chroma_dialog_height_multiplier, typedValue, true);
        float heightMultiplier = typedValue.getFloat();
        int height = (int) ((ad.getContext().getResources().getDisplayMetrics().heightPixels) * heightMultiplier);

        getResources().getValue(R.dimen.chroma_dialog_width_multiplier, typedValue, true);
        float widthMultiplier = typedValue.getFloat();
        int width = (int) ((ad.getContext().getResources().getDisplayMetrics().widthPixels) * widthMultiplier);

        if (ad.getWindow() != null)
            ad.getWindow().setLayout(width, height);
    }

    /**
     * Builder class for ChromaDialog objects. Provides a convenient way to set the various fields of a Chroma Dialog and generate fragment associated.
     */
    public static class Builder {
        private
        @ColorInt
        int initialColor = DEFAULT_COLOR;
        private ColorMode colorMode = DEFAULT_MODE;
        private IndicatorMode indicatorMode = IndicatorMode.DECIMAL;

        public Builder initialColor(@ColorInt int initialColor) {
            this.initialColor = initialColor;
            return this;
        }

        public Builder colorMode(ColorMode colorMode) {
            this.colorMode = colorMode;
            //TODO
            /*
            IndicatorMode indicatorMode = IndicatorMode.HEX;
            if(colorMode == ColorMode.HSV
                    || colorMode == ColorMode.CMYK
                    || colorMode == ColorMode.HSL) indicatorMode = IndicatorMode.DECIMAL; // cuz HEX is dumb for those
                    */
            return this;
        }

        public Builder indicatorMode(IndicatorMode indicatorMode) {
            this.indicatorMode = indicatorMode;
            return this;
        }

        public ChromaDialog create() {
            return newInstance(initialColor, colorMode, indicatorMode);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        assert dialog.getWindow() != null;
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                measureLayout((Dialog) dialog);
            }
        });

        return dialog;
    }

    /**
     * Get key associated to preference, or null if key not present
     *
     * @return a String value, or null
     */
    public String getKeyPreference() {
        return getArguments().getString(ARG_KEY);
    }

    /**
     * Get color listener if it was defined by setter else return null
     * @return
     */
    public OnColorSelectedListener getOnColorSelectedListener() {
        return onColorSelectedListener;
    }

    /**
     * Defined listener for click on positive and negative button
     * You can implement OnColorSelectedListener in activity or fragment without use this setter for a better usability
     * @param onColorSelectedListener
     */
    public void setOnColorSelectedListener(OnColorSelectedListener onColorSelectedListener) {
        this.onColorSelectedListener = onColorSelectedListener;
    }
}

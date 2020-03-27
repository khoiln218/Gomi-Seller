package vn.gomicorp.seller.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputLayout;

import vn.gomicorp.seller.utils.Utils;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public class InputBinding {
    @BindingAdapter("setErrorEnabled")
    public static void setErrorEnabled(TextInputLayout inputLayout, boolean enable) {
        inputLayout.setErrorEnabled(enable);
    }

    @BindingAdapter("setError")
    public static void setError(TextInputLayout inputLayout, String msg) {
        inputLayout.setError(msg);
        Utils.playVibrate(inputLayout.getContext());
    }

    @BindingAdapter("requestFocus")
    public static void requestFocus(View txt, boolean requestFocus) {
        if (requestFocus) {
            txt.setFocusableInTouchMode(true);
            txt.requestFocus();
        }
    }
}

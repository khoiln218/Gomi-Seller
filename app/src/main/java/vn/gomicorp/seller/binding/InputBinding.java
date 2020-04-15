package vn.gomicorp.seller.binding;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
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
        if (TextUtils.isEmpty(msg)) return;
        inputLayout.setError(msg);
        Utils.playVibrate(inputLayout.getContext());
    }

    @BindingAdapter("requestFocus")
    public static void requestFocus(View txt, boolean requestFocus) {
        if (requestFocus) {
            txt.clearFocus();
            txt.setFocusableInTouchMode(true);
            txt.requestFocus();
        }
    }

    @BindingAdapter("flag")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView)
                .load(imageUrl)
                .into(imageView);
    }
}

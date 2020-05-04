package vn.gomisellers.apps.binding;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import vn.gomisellers.apps.R;
import vn.gomisellers.apps.adapter.GenderAdapter;
import vn.gomisellers.apps.main.mypage.order.OrderAdapter;
import vn.gomisellers.apps.utils.DateTimes;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Strings;
import vn.gomisellers.apps.utils.Utils;

/**
 * Created by KHOI LE on 3/31/2020.
 */
public class MyPageBinding {

    @BindingAdapter("setText")
    public static void setCount(TextView textView, int count) {
        textView.setText(String.valueOf(count));
    }

    @BindingAdapter("setOrderDateCreate")
    public static void setOrderDateCreate(TextView textView, long date) {
        textView.setText(DateTimes.toString(date, GomiConstants.INFO_ORDER_DATE_FORMAT));
    }

    @BindingAdapter("setImageOrder")
    public static void setImageOrder(ImageView imageView, String imageUrl) {
        Glide.with(imageView)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_place_holder)
                        .override(Utils.getScreenWidth() / 3)
                        .centerCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }

    @BindingAdapter("setOrderAdapter")
    public static void setOrderAdapter(RecyclerView recyclerView, OrderAdapter adapter) {
        if (recyclerView.getAdapter() == null && adapter != null) {
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("setGenderAdapter")
    public static void setGenderAdapter(Spinner spinner, GenderAdapter adapter) {
        if (adapter != null && spinner.getAdapter() == null) {
            spinner.setAdapter(adapter);
        }
    }

    @BindingAdapter("setSelect")
    public static void setSelect(Spinner spinner, int position) {
        if (position < 0 || position > 2) position = 0;
        spinner.setSelection(position);
    }

    @BindingAdapter("setMyPageAvatar")
    public static void setMypageAvatar(ImageView imageView, String avatarUrl) {
        if (!Strings.isNullOrEmpty(avatarUrl)) {
            Glide.with(imageView)
                    .load(avatarUrl)
                    .apply(new RequestOptions()
                            .override(Utils.getScreenWidth() / 3)
                            .centerCrop()
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(imageView);
        }
    }

    @BindingAdapter("setAvatar")
    public static void setMypageAvatar(ImageView imageView, Uri avatarUri) {
        if (avatarUri == null) return;
        Glide.with(imageView)
                .load(avatarUri)
                .apply(new RequestOptions()
                        .override(Utils.getScreenWidth() / 3)
                        .centerCrop()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(imageView);
    }
}

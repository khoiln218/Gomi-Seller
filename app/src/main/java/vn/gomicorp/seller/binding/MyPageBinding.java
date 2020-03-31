package vn.gomicorp.seller.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import vn.gomicorp.seller.utils.Strings;
import vn.gomicorp.seller.utils.Utils;

/**
 * Created by KHOI LE on 3/31/2020.
 */
public class MyPageBinding {

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
}

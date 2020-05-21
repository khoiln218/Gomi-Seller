package vn.gomisellers.apps.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.stats.StatsManager;
import vn.gomisellers.apps.main.live.main.VideoGridContainer;

/**
 * Created by KHOI LE on 5/21/2020.
 */
public class LiveBinding {
    @BindingAdapter("initUserIcon")
    public static void initUserIcon(final ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .placeholder(R.drawable.fake_user_icon)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                .into(imageView);
    }

    @BindingAdapter("setStatsManager")
    public static void setStatsManager(VideoGridContainer videoGridContainer, StatsManager statsManager) {
        videoGridContainer.setStatsManager(statsManager);
    }
}

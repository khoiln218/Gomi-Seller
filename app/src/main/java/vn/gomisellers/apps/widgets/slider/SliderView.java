package vn.gomisellers.apps.widgets.slider;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;

import vn.gomisellers.apps.R;
import vn.gomisellers.apps.utils.Utils;


public class SliderView {

    private Context context;
    private String imagePath;
    private OnSliderClickListener onSliderClickListener;

    public SliderView(Context context) {
        this.context = context;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setOnSliderClickListener(OnSliderClickListener onSliderClickListener) {
        this.onSliderClickListener = onSliderClickListener;
    }

    public View getView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_image_slider_item, null, false);

        ShimmerFrameLayout shimmer = view.findViewById(R.id.shimmer_container);
        ImageView imageView = view.findViewById(R.id.image_slider);

        shimmer.startShimmer();
        bindData(view, shimmer, imageView);

        return view;
    }

    private void bindData(View view, final ShimmerFrameLayout shimmer, final ImageView imageView) {

        final SliderView sliderView = this;

        Glide.with(context)
                .load(getImagePath())
                .apply(new RequestOptions()
                        .override(Utils.getScreenWidth())
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        shimmer.stopShimmer();
                        shimmer.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSliderClickListener != null)
                    onSliderClickListener.onSliderClick(sliderView);
            }
        });
    }

    public interface OnSliderClickListener {
        void onSliderClick(SliderView sliderView);
    }
}

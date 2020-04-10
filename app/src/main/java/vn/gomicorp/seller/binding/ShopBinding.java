package vn.gomicorp.seller.binding;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.LocationAdapter;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Utils;
import vn.gomicorp.seller.widgets.PrefixEditText;

/**
 * Created by KHOI LE on 4/10/2020.
 */
public class ShopBinding {
    @BindingAdapter("init")
    public static void init(PrefixEditText prefixEditText, Void _v) {
        String sellerUrl = EappsApplication.getPreferences().getSellerUrl();
        prefixEditText.setPrefix(sellerUrl);
        prefixEditText.setTag(sellerUrl);
    }

    @BindingAdapter("setHintShopDesc")
    public static void setHintShopDesc(EditText editText, String hint) {
        editText.setHint(String.format(hint, GomiConstants.MAX_CHAR));
    }

    @BindingAdapter("setLocationAdapter")
    public static void setAdapterCountry(Spinner spinner, LocationAdapter adapter) {
        if (adapter != null && spinner.getAdapter() == null) {
            spinner.setAdapter(adapter);
        }
    }

    @BindingAdapter("initBannerContainer")
    public static void initBannerContainer(View view, Void _v) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth() * (3 / 4f));
    }

    @BindingAdapter("setImageView")
    public static void loadImage(ImageView view, Uri imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.img_home_banner)
                        .override(Utils.getScreenWidth())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(view);
    }
}

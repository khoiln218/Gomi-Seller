package vn.gomisellers.apps.binding;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;
import java.util.Locale;

import vn.gomisellers.apps.R;
import vn.gomisellers.apps.adapter.AttributeAdapter;
import vn.gomisellers.apps.adapter.CategoryItemAdapter;
import vn.gomisellers.apps.adapter.MarketListAdapter;
import vn.gomisellers.apps.adapter.ProductDetailAdapter;
import vn.gomisellers.apps.adapter.ProductItemAdapter;
import vn.gomisellers.apps.data.source.model.data.Attribute;
import vn.gomisellers.apps.data.source.model.data.Banner;
import vn.gomisellers.apps.data.source.model.data.Collection;
import vn.gomisellers.apps.main.market.collection.cate.CategoryAdapter;
import vn.gomisellers.apps.utils.Numbers;
import vn.gomisellers.apps.utils.Utils;
import vn.gomisellers.apps.widgets.slider.SliderLayout;
import vn.gomisellers.apps.widgets.slider.SliderView;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class MainBinding {
    private static final int INTRODUCE_ROW = 2;

    @BindingAdapter("initLayout")
    public static void initLayout(View view, Void _v) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth() * 1.5f);
        view.setLayoutParams(params);
    }

    @BindingAdapter("setAttributeContent")
    public static void setAttributeContent(final TextView textView, String content) {
        textView.setText(Utils.fromHtml(content));
        textView.post(new Runnable() {
            @Override
            public void run() {
                if (textView.getLineCount() > 3) {
                    textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
                    textView.setPadding(0, 10, 0, 0);
                }
            }
        });
    }

    @BindingAdapter("setAttributes")
    public static void setAttributes(RecyclerView recyclerView, List<Attribute> attributeList) {
        if (recyclerView.getAdapter() == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext()) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

            AttributeAdapter adapter = new AttributeAdapter(attributeList);
            recyclerView.setAdapter(adapter);
        } else {
            ((AttributeAdapter) recyclerView.getAdapter()).setAttributes(attributeList);
        }
    }

    @BindingAdapter({"setTitle", "isShow"})
    public static void setTitleCollapsing(CollapsingToolbarLayout collapsing, String title, boolean isShow) {
        collapsing.setTitle(isShow ? title : "");
    }

    @BindingAdapter("setSaleOff")
    public static void setSaleOff(TextView textView, int saleOff) {
        textView.setText(String.format(Locale.getDefault(), "-%d%%", saleOff));
    }

    @BindingAdapter("setProductDetailAdapter")
    public static void setProduct(RecyclerView recyclerView, ProductDetailAdapter adapter) {
        if (adapter != null && recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }

    }

    @BindingAdapter("setImageSlider")
    public static void setImageSlider(SliderLayout sliderLayout, List<String> imageUrlList) {
        if (imageUrlList == null || imageUrlList.size() == 0)
            return;
        sliderLayout.clearSliderView();
        for (String imageUrl : imageUrlList) {
            SliderView sliderView = new SliderView(sliderLayout.getContext());
            sliderView.setImagePath(imageUrl);
            sliderLayout.addSliderView(sliderView);
        }
    }

    @BindingAdapter("refreshing")
    public static void setRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @BindingAdapter("setLayoutLoading")
    public static void setLayoutLoading(View view, Void _v) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            params.setFullSpan(true);

            view.setLayoutParams(params);
        }
    }

    @BindingAdapter("setBannerSlider")
    public static void setBannerSlider(SliderLayout slider, Collection collection) {
        slider.clearSliderView();
        for (Parcelable parcelable : collection.getData()) {
            if (parcelable instanceof Banner) {
                SliderView sliderView = new SliderView(slider.getContext());
                sliderView.setImagePath(((Banner) parcelable).getImagePath());
                slider.addSliderView(sliderView);
            }
        }
    }

    @BindingAdapter("setHeight")
    public static void setHeight(View view, int height) {
        view.getLayoutParams().height = height;
    }

    @BindingAdapter("setScrollTimeSec")
    public static void setScrollTimeSec(SliderLayout sliderLayout, int duration) {
        sliderLayout.setScrollTimeSec(duration);
    }

    @BindingAdapter("setCollectionAdapter")
    public static void setCollections(RecyclerView recyclerView, MarketListAdapter adapter) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("setMegaCategory")
    public static void setMegaCategory(RecyclerView recyclerView, CategoryItemAdapter adapter) {
        if (recyclerView.getAdapter() == null && adapter != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        }
    }

    @BindingAdapter("setCategoryAdapter")
    public static void setCategories(RecyclerView recyclerView, CategoryAdapter adapter) {
        if (adapter != null && recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("setCollectionAdapter")
    public static void setCollectionAdapter(RecyclerView recyclerView, ProductItemAdapter adapter) {
        if (adapter != null && recyclerView.getAdapter() == null) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(INTRODUCE_ROW, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            adapter.addOnScrollListener(recyclerView);
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("setAdapterProductCollection")
    public static void setAdapterProductCollection(RecyclerView recyclerView, ProductItemAdapter adapter) {
        if (recyclerView.getAdapter() == null && adapter != null) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(INTRODUCE_ROW, StaggeredGridLayoutManager.VERTICAL) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }

                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("setImageCategory")
    public static void setImageCategory(ImageView imageView, String icon) {
        Glide.with(imageView)
                .load(icon)
                .apply(new RequestOptions()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imageView);
    }

    @BindingAdapter("setImageSelectDialog")
    public static void setImageSelectDialog(ImageView imageView, String imageUrl) {
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

    @BindingAdapter("setImage")
    public static void setImage(ImageView imageView, String thumbnail) {
        int width = Utils.getScreenWidth() / INTRODUCE_ROW;
        Glide.with(imageView)
                .load(thumbnail)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_place_holder)
                        .override(width)
                        .centerCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(imageView);
    }

    @BindingAdapter("setPrice")
    public static void setPrice(TextView view, double price) {
        view.setText(Numbers.currencyFormat(price));
    }

    @BindingAdapter("setProfit")
    public static void setProfit(TextView view, double profit) {
        view.setText(String.format("+ %s", Numbers.currencyFormat(profit)));
    }
}

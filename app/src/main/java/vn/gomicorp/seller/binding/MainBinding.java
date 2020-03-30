package vn.gomicorp.seller.binding;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.AttributeAdapter;
import vn.gomicorp.seller.adapter.CategoryItemAdapter;
import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.adapter.ProductDetailAdapter;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.source.model.data.Attribute;
import vn.gomicorp.seller.data.source.model.data.Banner;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.CategoryHandler;
import vn.gomicorp.seller.event.CollectionHandler;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.OnLoadTabListener;
import vn.gomicorp.seller.event.ProductHandler;
import vn.gomicorp.seller.utils.Numbers;
import vn.gomicorp.seller.utils.Utils;
import vn.gomicorp.seller.widgets.slider.SliderLayout;
import vn.gomicorp.seller.widgets.slider.SliderView;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class MainBinding {
    private static final int INTRODUCE_ROW = 2;

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

    @BindingAdapter("selectChange")
    public static void selectChange(RecyclerView recyclerView, Product product) {
        if (recyclerView.getAdapter() != null) {
            ((ProductItemAdapter) recyclerView.getAdapter()).notifyItemChanged(product);
        }
    }

    @BindingAdapter({"setTitle", "isShow"})
    public static void setTitleCollapsing(CollapsingToolbarLayout collapsing, String title, boolean isShow) {
        collapsing.setTitle(isShow ? title : "");
    }

    @BindingAdapter("setSaleOff")
    public static void setSaleOff(TextView textView, int saleOff) {
        textView.setText(String.format("-%d%%", saleOff));
    }

    @BindingAdapter("setProduct")
    public static void setProduct(RecyclerView recyclerView, Product product) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            ProductDetailAdapter adapter = new ProductDetailAdapter(product);
            recyclerView.setAdapter(adapter);
        } else {
            ((ProductDetailAdapter) recyclerView.getAdapter()).setProduct(product);
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

    @BindingAdapter({"setCollectionCategories", "onLoadTabListener"})
    public static void setupTab(TabLayout tabLayout, final List<Category> categories, final OnLoadTabListener onLoadTabListener) {
        if (categories == null)
            return;
        for (Category cate : categories)
            tabLayout.addTab(tabLayout.newTab().setText(cate.getName()));

        if (tabLayout.getTabCount() > 0) {
            Category selectedCategory = categories.get(tabLayout.getSelectedTabPosition());
            if (onLoadTabListener != null)
                onLoadTabListener.onLoaded(selectedCategory);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() > categories.size())
                        return;

                    Category selectedCategory = categories.get(tab.getPosition());
                    if (onLoadTabListener != null)
                        onLoadTabListener.onLoaded(selectedCategory);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } else {
            if (onLoadTabListener != null)
                onLoadTabListener.onLoadFails();
        }
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

    @BindingAdapter({"setCollections", "productHandler", "categoryHandler", "collectionHandler", "selectChange"})
    public static void setCollections(RecyclerView recyclerView, List<Collection> collections, ProductHandler productHandler, CategoryHandler categoryHandler, CollectionHandler collectionHandler, Product productChange) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);

            MarketListAdapter adapter = new MarketListAdapter(collections, productHandler, categoryHandler, collectionHandler);
            recyclerView.setAdapter(adapter);
        } else {
            ((MarketListAdapter) recyclerView.getAdapter()).setCollections(collections);
            ((MarketListAdapter) recyclerView.getAdapter()).setProductChange(productChange);
        }
    }

    @BindingAdapter({"setCategories", "categoryHandler"})
    public static void setCategory(RecyclerView recyclerView, Collection collection, CategoryHandler categoryHandler) {
        List<Category> categoryList = new ArrayList<>();
        for (Parcelable parcelable : collection.getData()) {
            if (parcelable instanceof Category)
                categoryList.add((Category) parcelable);
        }
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            CategoryItemAdapter adapter = new CategoryItemAdapter(categoryList, categoryHandler);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        } else {
            ((CategoryItemAdapter) recyclerView.getAdapter()).setCategoryList(categoryList);
        }
    }

    @BindingAdapter({"setCategoryProducts", "productHandler", "onLoadMoreListener"})
    public static void setCategoryProducts(RecyclerView recyclerView, List<Product> products, ProductHandler productHandler, final OnLoadMoreListener onLoadMoreListener) {
        if (recyclerView.getAdapter() == null) {
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
            ProductItemAdapter adapter = new ProductItemAdapter(products, productHandler);
            if (onLoadMoreListener != null)
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                            final StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

                            int visibleItemCount = layoutManager.getChildCount();
                            int totalItemCount = layoutManager.getItemCount();
                            int pastVisibleItems = 0;

                            int[] firstVisibleItems = null;
                            firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                            if (firstVisibleItems != null && firstVisibleItems.length > 0)
                                pastVisibleItems = firstVisibleItems[0];

                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount)
                                onLoadMoreListener.onLoadMore();
                        }
                    }
                });
            recyclerView.setAdapter(adapter);
        } else {
            ((ProductItemAdapter) recyclerView.getAdapter()).setProductList(products);
        }
    }

    @BindingAdapter({"setProducts", "productHandler", "onLoadMoreListener"})
    public static void setProducts(RecyclerView recyclerView, Collection collection, ProductHandler productHandler, OnLoadMoreListener onLoadMoreListener) {
        List<Product> products = new ArrayList<>();
        for (Parcelable parcelable : collection.getData()) {
            if (parcelable instanceof Product)
                products.add((Product) parcelable);
        }
        setCategoryProducts(recyclerView, products, productHandler, onLoadMoreListener);
    }

    @BindingAdapter("setImageCategory")
    public static void setImageCategory(ImageView view, String icon) {
        Glide.with(view.getContext())
                .load(icon)
                .apply(new RequestOptions()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(view);
    }

    @BindingAdapter("setImageSelectDialog")
    public static void setImageSelectDialog(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_place_holder)
                        .override(Utils.getScreenWidth() / 3)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(view);
    }

    @BindingAdapter("setImage")
    public static void setImage(ImageView view, String thumbnail) {
        int width = Utils.getScreenWidth() / INTRODUCE_ROW;
        Glide.with(view.getContext())
                .load(thumbnail)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_place_holder)
                        .override(width)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(view);
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

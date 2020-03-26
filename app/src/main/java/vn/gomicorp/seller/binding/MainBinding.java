package vn.gomicorp.seller.binding;

import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.CategoryItemAdapter;
import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.data.source.model.data.MegaCateListBean;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.ProductHandler;
import vn.gomicorp.seller.utils.Numbers;
import vn.gomicorp.seller.utils.Utils;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class MainBinding {
    private static final int INTRODUCE_ROW = 2;

    @BindingAdapter({"setCollections", "listener"})
    public static void setCollections(RecyclerView recyclerView, List<Collection> collections, ProductHandler listener) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);

            MarketListAdapter adapter = new MarketListAdapter(collections, listener);
            recyclerView.setAdapter(adapter);
        } else {
            ((MarketListAdapter) recyclerView.getAdapter()).setCollections(collections);
        }
    }

    @BindingAdapter("setCategorys")
    public static void setCategory(RecyclerView recyclerView, Collection collection) {
        List<MegaCateListBean> categoryList = new ArrayList<>();
        for (Parcelable parcelable : collection.getData()) {
            if (parcelable instanceof MegaCateListBean)
                categoryList.add((MegaCateListBean) parcelable);
        }
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            CategoryItemAdapter adapter = new CategoryItemAdapter(categoryList);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        } else {
            ((CategoryItemAdapter) recyclerView.getAdapter()).setCategoryList(categoryList);
        }
    }

    @BindingAdapter({"setProducts", "listener"})
    public static void setProducts(RecyclerView recyclerView, Collection collection, ProductHandler listener) {
        List<Product> products = new ArrayList<>();
        for (Parcelable parcelable : collection.getData()) {
            if (parcelable instanceof Product)
                products.add((Product) parcelable);
        }
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
            ProductItemAdapter adapter = new ProductItemAdapter(products, listener);
            recyclerView.setAdapter(adapter);
        } else {
            ((ProductItemAdapter) recyclerView.getAdapter()).setProductList(products);
        }
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

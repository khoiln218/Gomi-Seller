package vn.gomicorp.seller.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomicorp.seller.adapter.holder.BannerSliderHolder;
import vn.gomicorp.seller.adapter.holder.CategoryHolder;
import vn.gomicorp.seller.adapter.holder.LoadingHolder;
import vn.gomicorp.seller.adapter.holder.ProductHolder;
import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.event.CategoryHandler;
import vn.gomicorp.seller.event.CollectionHandler;
import vn.gomicorp.seller.event.OnProductAdapterInitListener;
import vn.gomicorp.seller.event.ProductHandler;

public class MarketListAdapter extends RecyclerView.Adapter {
    private List<Collection> collections;
    private ProductHandler productHandler;
    private CategoryHandler categoryHandler;
    private CollectionHandler collectionHandler;
    private OnProductAdapterInitListener onProductAdapterInitListener;

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
        notifyDataSetChanged();
    }

    public MarketListAdapter(List<Collection> collections, ProductHandler productHandler, CategoryHandler categoryHandler, CollectionHandler collectionHandler, OnProductAdapterInitListener onProductAdapterInitListener) {
        this.collections = collections;
        this.productHandler = productHandler;
        this.categoryHandler = categoryHandler;
        this.collectionHandler = collectionHandler;
        this.onProductAdapterInitListener = onProductAdapterInitListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CollectionType.BANNER:
                return BannerSliderHolder.getInstance(parent);
            case CollectionType.MEGA_CATAGORY:
                return CategoryHolder.getInstance(parent);
            case CollectionType.NEW_PRODUCT:
            case CollectionType.RECOMEND_PRODUCT:
            case CollectionType.SEEN_PRODUCT:
                return ProductHolder.getInstance(parent);
            default:
                return LoadingHolder.getInstance(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerSliderHolder)
            ((BannerSliderHolder) holder).setBannerSlider(collections.get(position));

        else if (holder instanceof CategoryHolder)
            ((CategoryHolder) holder).setCategoryList(collections.get(position), categoryHandler);

        else if (holder instanceof ProductHolder) {
            ((ProductHolder) holder).bind(collections.get(position), productHandler, collectionHandler, onProductAdapterInitListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Collection collection = collections.get(position);
        if (collection == null) {
            return CollectionType.VIEW_LOADING;
        }
        return collection.getType();
    }

    @Override
    public int getItemCount() {
        return collections == null ? 0 : collections.size();
    }

    public interface CollectionType {
        int SEEN_PRODUCT = -5;
        int MEGA_CATAGORY = -4;
        int CATAGORY = -3;
        int SUB_CATAGORY = -2;
        int BANNER = -1;
        int VIEW_LOADING = 0;
        int NEW_PRODUCT = 1;
        int RECOMEND_PRODUCT = 2;
    }
}

package vn.gomicorp.seller.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomicorp.seller.adapter.holder.BannerSliderHolder;
import vn.gomicorp.seller.adapter.holder.CategoryHolder;
import vn.gomicorp.seller.adapter.holder.ProductHolder;
import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.event.ProductHandler;

public class MarketListAdapter extends RecyclerView.Adapter {
    private List<Collection> collections;
    private ProductHandler listener;

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
        notifyDataSetChanged();
    }

    public MarketListAdapter(List<Collection> collections, ProductHandler listener) {
        this.collections = collections;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CollectionType.BANNER:
                return BannerSliderHolder.getInstance(parent);
            case CollectionType.CATAGORY:
                return CategoryHolder.getInstance(parent);
            case CollectionType.NEW_PRODUCT:
            case CollectionType.RECOMEND_PRODUCT:
            case CollectionType.SEEN_PRODUCT:
                return ProductHolder.getInstance(parent);
            default:
                return new LoadingHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerSliderHolder)
            ((BannerSliderHolder) holder).setBannerSlider(collections.get(position));

        else if (holder instanceof CategoryHolder)
            ((CategoryHolder) holder).setCategoryList(collections.get(position));

        else if (holder instanceof ProductHolder) {
            ((ProductHolder) holder).bind(collections.get(position), listener);
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
        int VIEW_LOADING = 0;
        int NEW_PRODUCT = 1;
        int RECOMEND_PRODUCT = 2;
        int BANNER = 3;
        int CATAGORY = 4;
        int SEEN_PRODUCT = 5;
    }

    private class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(ViewGroup viewGroup) {
            super(viewGroup);
        }
    }
}

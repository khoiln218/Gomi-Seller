package vn.gomisellers.apps.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.adapter.holder.BannerSliderHolder;
import vn.gomisellers.apps.adapter.holder.CategoryHolder;
import vn.gomisellers.apps.adapter.holder.LoadingHolder;
import vn.gomisellers.apps.adapter.holder.ProductHolder;
import vn.gomisellers.apps.data.source.model.data.Category;
import vn.gomisellers.apps.data.source.model.data.Collection;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.event.CollectionHandler;
import vn.gomisellers.apps.main.market.MarketViewModel;

public class MarketListAdapter extends RecyclerView.Adapter {
    private List<Collection> collections;
    private CollectionHandler collectionHandler;

    private CategoryItemAdapter categoryItemAdapter;
    private ProductItemAdapter newProductAdapter;
    private ProductItemAdapter recommendProductAdapter;
    private ProductItemAdapter seenProductAdapter;

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
        notifyDataSetChanged();
    }

    public MarketListAdapter(List<Collection> collections, MarketViewModel viewModel) {
        this.collections = collections;
        this.collectionHandler = viewModel;

        categoryItemAdapter = new CategoryItemAdapter(new ArrayList<Category>(), viewModel);
        newProductAdapter = new ProductItemAdapter(new ArrayList<Product>(), viewModel, null);
        recommendProductAdapter = new ProductItemAdapter(new ArrayList<Product>(), viewModel, null);
        seenProductAdapter = new ProductItemAdapter(new ArrayList<Product>(), viewModel, null);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CollectionType.BANNER:
                return BannerSliderHolder.getInstance(parent);
            case CollectionType.MEGA_CATEGORY:
                return CategoryHolder.getInstance(parent);
            case CollectionType.NEW_PRODUCT:
            case CollectionType.RECOMMEND_PRODUCT:
            case CollectionType.SEEN_PRODUCT:
                return ProductHolder.getInstance(parent);
            default:
                return LoadingHolder.getInstance(parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Collection collection = collections.get(position);
        switch (collection.getType()) {
            case CollectionType.BANNER:
                ((BannerSliderHolder) holder).setBannerSlider(collection);
                break;
            case CollectionType.MEGA_CATEGORY:
                ((CategoryHolder) holder).setCategoryList(collection, categoryItemAdapter);
                break;
            case CollectionType.NEW_PRODUCT:
                ((ProductHolder) holder).bind(collection, newProductAdapter, collectionHandler);
                break;
            case CollectionType.RECOMMEND_PRODUCT:
                ((ProductHolder) holder).bind(collection, recommendProductAdapter, collectionHandler);
                break;
            case CollectionType.SEEN_PRODUCT:
                ((ProductHolder) holder).bind(collection, seenProductAdapter, collectionHandler);
                break;
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

    public void notifyItemChanged(Product product) {
        newProductAdapter.notifyItemChanged(product);
        recommendProductAdapter.notifyItemChanged(product);
        seenProductAdapter.notifyItemChanged(product);
    }

    public interface CollectionType {
        int SEEN_PRODUCT = -3;
        int MEGA_CATEGORY = -2;
        int BANNER = -1;
        int VIEW_LOADING = 0;
        int NEW_PRODUCT = 1;
        int RECOMMEND_PRODUCT = 2;
    }
}

package vn.gomicorp.seller.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.databinding.ListProductBinding;
import vn.gomicorp.seller.event.CollectionHandler;
import vn.gomicorp.seller.event.ProductHandler;

public class ProductHolder extends RecyclerView.ViewHolder {

    private ListProductBinding binding;

    public static ProductHolder getInstance(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListProductBinding binding = ListProductBinding.inflate(layoutInflater, viewGroup, false);
        return new ProductHolder(binding);
    }

    public ProductHolder(ListProductBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Collection collection, ProductHandler productHandler, CollectionHandler collectionHandler) {
        binding.setCollection(collection);
        binding.setProductHandler(productHandler);
        binding.setCollectionHandler(collectionHandler);
        binding.executePendingBindings();
    }
}
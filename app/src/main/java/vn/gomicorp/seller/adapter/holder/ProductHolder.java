package vn.gomicorp.seller.adapter.holder;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.ListProductBinding;
import vn.gomicorp.seller.event.CollectionHandler;

public class ProductHolder extends RecyclerView.ViewHolder {

    private ListProductBinding binding;

    public static ProductHolder getInstance(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListProductBinding binding = ListProductBinding.inflate(layoutInflater, viewGroup, false);
        return new ProductHolder(binding);
    }

    private ProductHolder(ListProductBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Collection collection, ProductItemAdapter adapter, CollectionHandler collectionHandler) {
        List<Product> products = new ArrayList<>();
        for (Parcelable item : collection.getData()) {
            products.add((Product) item);
        }
        adapter.setProductList(products);
        binding.setCollection(collection);
        binding.setAdapter(adapter);
        binding.setCollectionHandler(collectionHandler);
        binding.executePendingBindings();
    }
}
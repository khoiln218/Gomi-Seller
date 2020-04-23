package vn.gomisellers.apps.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.databinding.ListProductItemBinding;
import vn.gomisellers.apps.event.ProductHandler;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class ProductItemHolder extends RecyclerView.ViewHolder {

    private ListProductItemBinding binding;

    public static ProductItemHolder getInstance(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListProductItemBinding binding = ListProductItemBinding.inflate(layoutInflater, viewGroup, false);
        return new ProductItemHolder(binding);
    }

    public ProductItemHolder(ListProductItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Product product, ProductHandler productHandler) {
        binding.setProduct(product);
        binding.setProductHandler(productHandler);
        binding.executePendingBindings();
    }
}

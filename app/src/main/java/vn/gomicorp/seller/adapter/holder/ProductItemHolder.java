package vn.gomicorp.seller.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.ListProductItemBinding;
import vn.gomicorp.seller.event.ProductHandler;

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

    public void bind(Product product, ProductHandler listener, ProductItemAdapter.ToggleProductHandler toggleProductHandler) {
        binding.setProduct(product);
        binding.setListener(listener);
        binding.setToggleItem(toggleProductHandler);
        binding.executePendingBindings();
    }
}

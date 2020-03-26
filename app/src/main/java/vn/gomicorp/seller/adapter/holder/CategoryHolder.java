package vn.gomicorp.seller.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.databinding.ListCategoryBinding;

public class CategoryHolder extends RecyclerView.ViewHolder {
    private ListCategoryBinding binding;

    public static CategoryHolder getInstance(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListCategoryBinding binding = ListCategoryBinding.inflate(layoutInflater, viewGroup, false);
        return new CategoryHolder(binding);
    }

    private CategoryHolder(ListCategoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setCategoryList(Collection collection) {
        binding.setCollection(collection);
        binding.executePendingBindings();
    }
}
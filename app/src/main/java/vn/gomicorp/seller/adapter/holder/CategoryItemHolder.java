package vn.gomicorp.seller.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import vn.gomicorp.seller.data.source.model.data.MegaCateListBean;
import vn.gomicorp.seller.databinding.ListCategoryItemBinding;

/**
 * Created by KHOI LE on 3/25/2020.
 */
public class CategoryItemHolder extends RecyclerView.ViewHolder {
    private ListCategoryItemBinding binding;

    public static CategoryItemHolder getInstance(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListCategoryItemBinding binding = ListCategoryItemBinding.inflate(layoutInflater, viewGroup, false);
        return new CategoryItemHolder(binding);
    }

    private CategoryItemHolder(ListCategoryItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setCategory(MegaCateListBean category) {
        binding.setCategory(category);
        binding.executePendingBindings();
    }
}

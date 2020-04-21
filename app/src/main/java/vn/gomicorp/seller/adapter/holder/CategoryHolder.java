package vn.gomicorp.seller.adapter.holder;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.adapter.CategoryItemAdapter;
import vn.gomicorp.seller.data.source.model.data.Category;
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

    public void setCategoryList(Collection collection, CategoryItemAdapter adapter) {
        List<Category> categoryList = new ArrayList<>();
        for (Parcelable parcelable : collection.getData()) {
            if (parcelable instanceof Category)
                categoryList.add((Category) parcelable);
        }
        adapter.setCategoryList(categoryList);
        binding.setAdapter(adapter);
        binding.executePendingBindings();
    }
}
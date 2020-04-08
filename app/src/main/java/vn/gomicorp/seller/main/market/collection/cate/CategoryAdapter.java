package vn.gomicorp.seller.main.market.collection.cate;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.databinding.ListCategoryItemRoundBinding;
import vn.gomicorp.seller.event.CategoryHandler;

/**
 * Created by KHOI LE on 4/7/2020.
 */
public class CategoryAdapter extends RecyclerView.Adapter {
    private static final int NOT_FOUND = -1;

    private List<Category> categoryList;
    private CategoryHandler categoryHandler;
    private int currentSelect;

    public CategoryAdapter(List<Category> categoryList, CategoryHandler categoryHandler) {
        this.categoryList = categoryList;
        this.categoryHandler = categoryHandler;
        currentSelect = NOT_FOUND;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    public void selectItem(int id) {
        int pos = getPosition(id);
        if (pos != NOT_FOUND) {
            clearSellect();
            categoryList.get(pos).setSelect(true);
            currentSelect = pos;
            notifyItemChanged(pos);
        }
    }

    private void clearSellect() {
        if (currentSelect != NOT_FOUND) {
            categoryList.get(currentSelect).setSelect(false);
            notifyItemChanged(currentSelect);
        }
        currentSelect = NOT_FOUND;
    }

    private int getPosition(int id) {
        if (getItemCount() > 0) {
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == id)
                    return i;
            }
        }
        return NOT_FOUND;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CategoryHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CategoryHolder) holder).bind(categoryList.get(position), categoryHandler);
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    static class CategoryHolder extends RecyclerView.ViewHolder {
        private ListCategoryItemRoundBinding binding;

        public static CategoryHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            ListCategoryItemRoundBinding binding = ListCategoryItemRoundBinding.inflate(inflater, viewGroup, false);
            return new CategoryHolder(binding);
        }

        public CategoryHolder(ListCategoryItemRoundBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category, CategoryHandler categoryHandler) {
            binding.setCategory(category);
            binding.setCategoryHandler(categoryHandler);
            binding.executePendingBindings();
        }
    }
}

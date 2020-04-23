package vn.gomisellers.apps.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomisellers.apps.adapter.holder.CategoryItemHolder;
import vn.gomisellers.apps.data.source.model.data.Category;
import vn.gomisellers.apps.event.CategoryHandler;

/**
 * Created by KHOI LE on 3/25/2020.
 */
public class CategoryItemAdapter extends RecyclerView.Adapter {
    private List<Category> categoryList;
    private CategoryHandler listener;

    public CategoryItemAdapter(List<Category> categoryList, CategoryHandler listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CategoryItemHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CategoryItemHolder) holder).bind(categoryList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }
}

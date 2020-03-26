package vn.gomicorp.seller.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomicorp.seller.adapter.holder.CategoryItemHolder;
import vn.gomicorp.seller.data.source.model.data.MegaCateListBean;

/**
 * Created by KHOI LE on 3/25/2020.
 */
public class CategoryItemAdapter extends RecyclerView.Adapter {
    private List<MegaCateListBean> categoryList;

    public CategoryItemAdapter(List<MegaCateListBean> categoryList) {
        this.categoryList = categoryList;
    }

    public void setCategoryList(List<MegaCateListBean> categoryList) {
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
        ((CategoryItemHolder) holder).setCategory(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }
}

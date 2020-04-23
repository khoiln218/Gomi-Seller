package vn.gomisellers.apps.main.market.collection.subcate;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import vn.gomisellers.apps.main.market.collection.subcate.pager.ProductCategoryFragment;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class SubCategoryAdapter extends FragmentStateAdapter {
    private List<CategoryItem> categoryItemList;

    SubCategoryAdapter(@NonNull FragmentActivity fragmentActivity, List<CategoryItem> categoryItems) {
        super(fragmentActivity);
        this.categoryItemList = categoryItems;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int type = categoryItemList.get(position).getType();
        int id = categoryItemList.get(position).getId();
        return ProductCategoryFragment.newInstance(type, id);
    }

    @Override
    public int getItemCount() {
        return categoryItemList == null ? 0 : categoryItemList.size();
    }
}

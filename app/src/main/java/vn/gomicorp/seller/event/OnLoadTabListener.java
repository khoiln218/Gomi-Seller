package vn.gomicorp.seller.event;

import vn.gomicorp.seller.data.source.model.data.Category;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public interface OnLoadTabListener {
    void onLoaded(Category selectedCategory);

    void onLoadFails();
}

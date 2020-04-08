package vn.gomicorp.seller.main.market.collection.cate;

import vn.gomicorp.seller.data.source.model.data.Product;

/**
 * Created by KHOI LE on 4/7/2020.
 */
public interface CategoryListener {
    void openCategory(int type, int id, String name);

    void pick(Product product);
}

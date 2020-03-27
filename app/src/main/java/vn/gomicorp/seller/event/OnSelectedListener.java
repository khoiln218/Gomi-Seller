package vn.gomicorp.seller.event;

import vn.gomicorp.seller.data.source.model.data.Product;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public interface OnSelectedListener {
    void onSelected(Product product);
}

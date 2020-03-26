package vn.gomicorp.seller.event;

import vn.gomicorp.seller.data.source.model.data.Product;

/**
 * Created by KHOI LE on 3/25/2020.
 */
public interface ProductHandler {
    void onSelect(Product product);

    void onPick(Product product);
}

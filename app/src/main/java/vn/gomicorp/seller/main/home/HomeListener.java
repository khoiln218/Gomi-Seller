package vn.gomicorp.seller.main.home;

import vn.gomicorp.seller.data.source.model.data.Product;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public interface HomeListener {
    void show(Product product);

    void remove(Product product);

    void withdrawn();

    void shareSNS(String content);
}

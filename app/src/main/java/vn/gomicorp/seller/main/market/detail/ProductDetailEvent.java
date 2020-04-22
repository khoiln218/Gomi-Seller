package vn.gomicorp.seller.main.market.detail;

import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/21/2020.
 */
class ProductDetailEvent extends BaseEvent<Product> {
    static final int SHOW_DETAIL = 0;

    ProductDetailEvent(int code) {
        super(code);
    }
}

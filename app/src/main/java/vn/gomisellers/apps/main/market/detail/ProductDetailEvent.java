package vn.gomisellers.apps.main.market.detail;

import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/21/2020.
 */
class ProductDetailEvent extends BaseEvent<Product> {
    static final int SHOW_DETAIL = 0;

    ProductDetailEvent(int code) {
        super(code);
    }
}

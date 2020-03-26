package vn.gomicorp.seller.event;

import vn.gomicorp.seller.data.source.model.data.Product;

public interface OnSelectedListener {
        void onSelected(Product product);
    }
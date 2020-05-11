package vn.gomisellers.apps.data;

import java.util.List;

import vn.gomisellers.apps.data.source.model.api.OrderDetailRequest;
import vn.gomisellers.apps.data.source.model.api.OrderRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Order;

/**
 * Created by KHOI LE on 5/11/2020.
 */
public interface OrderDataSource {
    void findbyshopid(OrderRequest request, int page, ResultListener<ResponseData<List<Order>>> callback);

    void findbyid(OrderDetailRequest request, ResultListener<ResponseData<Order>> callback);
}

package vn.gomisellers.apps.data;

import java.util.List;

import vn.gomisellers.apps.data.source.model.api.OrderRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.data.source.remote.OrderRemoteDataSource;

/**
 * Created by KHOI LE on 5/11/2020.
 */
public class OrderRepository implements OrderDataSource {
    private volatile static OrderRepository INSTANCE = null;
    private OrderDataSource mOrderDataSource;

    private OrderRepository() {
        mOrderDataSource = new OrderRemoteDataSource();
    }

    public static OrderRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (ProductRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void findbyshopid(OrderRequest request, int page, ResultListener<ResponseData<List<Order>>> callback) {
        mOrderDataSource.findbyshopid(request, page, callback);
    }
}

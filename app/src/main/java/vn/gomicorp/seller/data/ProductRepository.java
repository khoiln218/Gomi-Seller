package vn.gomicorp.seller.data;

import vn.gomicorp.seller.data.source.model.api.IntroduceRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Introduce;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.data.source.remote.ProductRemoteDataSource;
import vn.gomicorp.seller.data.source.test.MockupProductDataSource;
import vn.gomicorp.seller.utils.GomiConstants;

/**
 * Created by KHOI LE on 3/24/2020.
 */
public class ProductRepository implements ProductDataSource {
    private volatile static ProductRepository INSTANCE = null;
    private ProductDataSource mProductDataSource;

    private ProductRepository() {
        if (GomiConstants.TEST) {
            mProductDataSource = new MockupProductDataSource();
        } else {
            mProductDataSource = new ProductRemoteDataSource();
        }
    }

    public static ProductRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (AccountRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void introduce(IntroduceRequest request, ResultListener<ResponseData<Introduce>> callback) {
        mProductDataSource.introduce(request, callback);
    }

    @Override
    public void select(ToggleProductRequest request, ResultListener<ResponseData<Product>> callback) {
        mProductDataSource.select(request, callback);
    }
}

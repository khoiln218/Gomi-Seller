package vn.gomicorp.seller.data;

import java.util.List;

import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.CollectionByIdRequest;
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

    @Override
    public void findbycollection(CollectionByIdRequest request, int page, ResultListener<ResponseData<List<Product>>> callback) {
        mProductDataSource.findbycollection(request, page, callback);
    }

    @Override
    public void findbycategory(CategoryByIdRequest request, int page, ResultListener<ResponseData<List<Product>>> callback) {
        mProductDataSource.findbycategory(request, page, callback);
    }

    @Override
    public void findbyseen(CollectionByIdRequest request, int page, ResultListener<ResponseData<List<Product>>> callback) {
        mProductDataSource.findbyseen(request, page, callback);
    }
}

package vn.gomisellers.apps.data;

import java.util.List;

import vn.gomisellers.apps.data.source.model.api.CategoryByIdRequest;
import vn.gomisellers.apps.data.source.model.api.CollectionByIdRequest;
import vn.gomisellers.apps.data.source.model.api.IntroduceRequest;
import vn.gomisellers.apps.data.source.model.api.ProductDetailRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.ShopRequest;
import vn.gomisellers.apps.data.source.model.api.ToggleProductRequest;
import vn.gomisellers.apps.data.source.model.data.Introduce;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.data.source.remote.ProductRemoteDataSource;
import vn.gomisellers.apps.data.source.test.MockupProductDataSource;
import vn.gomisellers.apps.utils.GomiConstants;

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
            synchronized (ProductRepository.class) {
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

    @Override
    public void findbyid(ProductDetailRequest request, ResultListener<ResponseData<Product>> callback) {
        mProductDataSource.findbyid(request, callback);
    }

    @Override
    public void findbyshop(ShopRequest request, int page, ResultListener<ResponseData<List<Product>>> callback) {
        mProductDataSource.findbyshop(request, page, callback);
    }
}

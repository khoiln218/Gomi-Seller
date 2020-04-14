package vn.gomicorp.seller.data;

import java.util.List;

import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.CreateShopRequest;
import vn.gomicorp.seller.data.source.model.api.MegaCategoryRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ShopRequest;
import vn.gomicorp.seller.data.source.model.api.VerifyUrlRequest;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Shop;
import vn.gomicorp.seller.data.source.remote.ShopRemoteDataSource;
import vn.gomicorp.seller.data.source.test.MockShopDataSource;
import vn.gomicorp.seller.utils.GomiConstants;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class ShopRepository implements ShopDataSource {
    private volatile static ShopRepository INSTANCE = null;
    private ShopDataSource mRemoteDataSource;

    private ShopRepository() {
        if (GomiConstants.TEST)
            mRemoteDataSource = new MockShopDataSource();
        else
            mRemoteDataSource = new ShopRemoteDataSource();
    }

    public static ShopRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (ShopRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ShopRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void verifySellerUrl(VerifyUrlRequest request, ResultListener<ResponseData> callback) {
        checkNotNull(callback);
        mRemoteDataSource.verifySellerUrl(request, callback);
    }

    @Override
    public void create(CreateShopRequest request, ResultListener<ResponseData<Shop>> callback) {
        checkNotNull(callback);
        mRemoteDataSource.create(request, callback);
    }

    @Override
    public void findcatebytype(CategoryByIdRequest request, ResultListener<ResponseData<List<Category>>> callback) {
        mRemoteDataSource.findcatebytype(request, callback);
    }

    @Override
    public void megacategory(MegaCategoryRequest request, ResultListener<ResponseData<List<Category>>> callback) {
        mRemoteDataSource.megacategory(request, callback);
    }

    @Override
    public void findbyid(ShopRequest request, ResultListener<ResponseData<Shop>> callback) {
        mRemoteDataSource.findbyid(request, callback);
    }
}

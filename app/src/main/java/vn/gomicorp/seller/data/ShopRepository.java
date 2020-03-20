package vn.gomicorp.seller.data;

import vn.gomicorp.seller.data.source.model.api.CreateShopRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.VerifyUrlRequest;
import vn.gomicorp.seller.data.source.model.data.Shop;
import vn.gomicorp.seller.data.source.remote.ShopRemoteDataSource;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class ShopRepository implements ShopDataSource {
    private volatile static ShopRepository INSTANCE = null;
    private ShopDataSource mRemoteDataSource;

    private ShopRepository() {
        mRemoteDataSource = new ShopRemoteDataSource();
    }

    public static ShopRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (LocationRepository.class) {
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
}

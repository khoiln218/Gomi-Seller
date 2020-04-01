package vn.gomicorp.seller.data;

import java.util.List;

import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.CreateShopRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.VerifyUrlRequest;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Shop;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public interface ShopDataSource {
    void verifySellerUrl(VerifyUrlRequest request, ResultListener<ResponseData> callback);

    void create(CreateShopRequest request, ResultListener<ResponseData<Shop>> callback);

    void findcatebytype(CategoryByIdRequest request, ResultListener<ResponseData<List<Category>>> callback);
}

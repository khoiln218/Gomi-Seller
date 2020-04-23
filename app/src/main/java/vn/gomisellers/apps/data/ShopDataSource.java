package vn.gomisellers.apps.data;

import java.util.List;

import vn.gomisellers.apps.data.source.model.api.CategoryByIdRequest;
import vn.gomisellers.apps.data.source.model.api.CreateShopRequest;
import vn.gomisellers.apps.data.source.model.api.MegaCategoryRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.ShopRequest;
import vn.gomisellers.apps.data.source.model.api.VerifyUrlRequest;
import vn.gomisellers.apps.data.source.model.data.Category;
import vn.gomisellers.apps.data.source.model.data.Shop;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public interface ShopDataSource {
    void verifySellerUrl(VerifyUrlRequest request, ResultListener<ResponseData> callback);

    void create(CreateShopRequest request, ResultListener<ResponseData<Shop>> callback);

    void findcatebytype(CategoryByIdRequest request, ResultListener<ResponseData<List<Category>>> callback);

    void megacategory(MegaCategoryRequest request, ResultListener<ResponseData<List<Category>>> callback);

    void findbyid(ShopRequest request, ResultListener<ResponseData<Shop>> callback);
}

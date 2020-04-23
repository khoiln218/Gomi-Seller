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

/**
 * Created by KHOI LE on 3/24/2020.
 */
public interface ProductDataSource {
    void introduce(IntroduceRequest request, ResultListener<ResponseData<Introduce>> callback);

    void select(ToggleProductRequest request, ResultListener<ResponseData<Product>> callback);

    void findbycollection(CollectionByIdRequest request, int page, ResultListener<ResponseData<List<Product>>> callback);

    void findbycategory(CategoryByIdRequest request, int page, ResultListener<ResponseData<List<Product>>> callback);

    void findbyseen(CollectionByIdRequest request, int page, ResultListener<ResponseData<List<Product>>> callback);

    void findbyid(ProductDetailRequest request, ResultListener<ResponseData<Product>> callback);

    void findbyshop(ShopRequest request, int page, ResultListener<ResponseData<List<Product>>> callback);
}

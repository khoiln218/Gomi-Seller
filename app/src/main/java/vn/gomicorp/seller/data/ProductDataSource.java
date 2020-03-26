package vn.gomicorp.seller.data;

import vn.gomicorp.seller.data.source.model.api.IntroduceRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Introduce;
import vn.gomicorp.seller.data.source.model.data.Product;

/**
 * Created by KHOI LE on 3/24/2020.
 */
public interface ProductDataSource {
    void introduce(IntroduceRequest request, ResultListener<ResponseData<Introduce>> callback);

    void select(ToggleProductRequest request, ResultListener<ResponseData<Product>> callback);
}

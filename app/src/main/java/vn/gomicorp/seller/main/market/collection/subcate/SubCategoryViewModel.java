package vn.gomicorp.seller.main.market.collection.subcate;

import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.ShopRepository;
import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Category;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class SubCategoryViewModel extends ViewModel {
    private static final int CODE_OK = 200;

    private ShopRepository mShopRepository = ShopRepository.getInstance();

    void requestCategory(int type, int id, final ResultListener<List<Category>> listener) {
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(type);
        request.setFindById(id);
        mShopRepository.findcatebytype(request, new ResultListener<ResponseData<List<Category>>>() {
            @Override
            public void onLoaded(ResponseData<List<Category>> result) {
                if (result.getCode() == CODE_OK) {
                    listener.onLoaded(result.getResult());
                } else {
                    listener.onDataNotAvailable(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                listener.onDataNotAvailable(error);
            }
        });
    }
}

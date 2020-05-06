package vn.gomisellers.apps.main.market.collection.subcate;

import java.util.List;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.ShopRepository;
import vn.gomisellers.apps.data.source.model.api.CategoryByIdRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Category;
import vn.gomisellers.apps.data.source.model.data.CategoryType;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class SubCategoryViewModel extends BaseViewModel<BaseEvent> {

    private ShopRepository mShopRepository;

    public SubCategoryViewModel() {
        mShopRepository = ShopRepository.getInstance();
    }

    void requestCategory(int id, final ResultListener<List<Category>> listener) {
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(CategoryType.CATEGORY);
        request.setFindById(id);
        mShopRepository.findcatebytype(request, new ResultListener<ResponseData<List<Category>>>() {
            @Override
            public void onLoaded(ResponseData<List<Category>> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
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

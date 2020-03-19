package vn.gomicorp.seller.data;

import java.util.List;

import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Country;

/**
 * Created by KHOI LE on 3/18/2020.
 */
public interface LocationDataSource {
    void getLocationCountry(ResultListener<ResponseData<List<Country>>> callback);
}

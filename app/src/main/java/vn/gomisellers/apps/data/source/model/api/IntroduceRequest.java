package vn.gomisellers.apps.data.source.model.api;

import vn.gomisellers.apps.EappsApplication;

/**
 * Created by KHOI LE on 3/24/2020.
 */
public class IntroduceRequest extends BaseRequest {

    /**
     * ShopId : f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e
     */

    private String ShopId;

    public IntroduceRequest() {
        ShopId = EappsApplication.getPreferences().getShopId();
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }
}

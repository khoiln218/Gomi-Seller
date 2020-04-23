package vn.gomisellers.apps.data.source.model.api;

import vn.gomisellers.apps.EappsApplication;

/**
 * Created by KHOI LE on 4/13/2020.
 */
public class ShopRequest extends BaseRequest {

    /**
     * ShopId : f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e
     * FindById : 3
     */

    private String ShopId;
    private int FindById;

    public ShopRequest() {
        ShopId = EappsApplication.getPreferences().getShopId();
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }

    public int getFindById() {
        return FindById;
    }

    public void setFindById(int FindById) {
        this.FindById = FindById;
    }
}

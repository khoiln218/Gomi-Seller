package vn.gomicorp.seller.data.source.model.api;

import vn.gomicorp.seller.EappsApplication;

/**
 * Created by KHOI LE on 4/1/2020.
 */
public class CategoryByIdRequest extends BaseRequest {
    /**
     * ShopId : f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e
     * FindById : 3
     * CategoryType : 1
     */

    private String ShopId;
    private int FindById;
    private int CategoryType;

    public CategoryByIdRequest() {
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

    public int getCategoryType() {
        return CategoryType;
    }

    public void setCategoryType(int CategoryType) {
        this.CategoryType = CategoryType;
    }
}

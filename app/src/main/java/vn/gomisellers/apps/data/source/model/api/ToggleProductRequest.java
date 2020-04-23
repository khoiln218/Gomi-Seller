package vn.gomisellers.apps.data.source.model.api;

import vn.gomisellers.apps.EappsApplication;

/**
 * Created by KHOI LE on 3/25/2020.
 */
public class ToggleProductRequest extends BaseRequest {

    /**
     * ProductId : 347a551a-4744-4799-ba84-3613950362d3
     * IsSelling : 0
     * ShopId : f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e
     */

    private String ProductId;
    private int IsSelling;
    private String ShopId;

    public ToggleProductRequest() {
        ShopId = EappsApplication.getPreferences().getShopId();
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String ProductId) {
        this.ProductId = ProductId;
    }

    public int getIsSelling() {
        return IsSelling;
    }

    public void setIsSelling(int IsSelling) {
        this.IsSelling = IsSelling;
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }
}

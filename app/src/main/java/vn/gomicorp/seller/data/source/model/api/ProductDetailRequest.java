package vn.gomicorp.seller.data.source.model.api;

import vn.gomicorp.seller.EappsApplication;

/**
 * Created by KHOI LE on 4/8/2020.
 */
public class ProductDetailRequest extends BaseRequest {

    /**
     * UserId : a4c1dd3d-c271-47af-ba14-85e115c61719
     * ShopId : f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e
     * ProductId : 347a551a-4744-4799-ba84-3613950362d3
     */

    private String ShopId;
    private String ProductId;

    public ProductDetailRequest() {
        ShopId = EappsApplication.getPreferences().getShopId();
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String ProductId) {
        this.ProductId = ProductId;
    }
}

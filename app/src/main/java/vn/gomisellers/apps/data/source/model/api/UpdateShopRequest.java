package vn.gomisellers.apps.data.source.model.api;

import vn.gomisellers.apps.EappsApplication;

/**
 * Created by KHOI LE on 3/20/2020.
 */
public class UpdateShopRequest extends BaseRequest {
    /**
     * ShopName : Khoi Le
     * Cover :
     * WebAddress : khoile000
     * CountryId : 1
     * ProvinceId : 2
     * DistrictId : 215
     * Description : làm đẹp và sức khỏe
     * ShopId : f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e
     */

    private String ShopName;
    private String Cover;
    private String WebAddress;
    private int CountryId;
    private int ProvinceId;
    private int DistrictId;
    private String Description;
    private String ShopId;

    public UpdateShopRequest() {
        ShopId = EappsApplication.getPreferences().getShopId();
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String Cover) {
        this.Cover = Cover;
    }

    public String getWebAddress() {
        return WebAddress;
    }

    public void setWebAddress(String WebAddress) {
        this.WebAddress = WebAddress;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int CountryId) {
        this.CountryId = CountryId;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int ProvinceId) {
        this.ProvinceId = ProvinceId;
    }

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int DistrictId) {
        this.DistrictId = DistrictId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }
}

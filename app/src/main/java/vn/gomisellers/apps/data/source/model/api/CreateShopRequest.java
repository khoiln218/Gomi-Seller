package vn.gomisellers.apps.data.source.model.api;

/**
 * Created by KHOI LE on 3/20/2020.
 */
public class CreateShopRequest extends BaseRequest {

    /**
     * ShopName : Khoi Le
     * Cover :
     * WebAddress : khoile
     * CountryId : 1
     * ProvinceId : 2
     * DistrictId : 215
     * Description : fashion
     * UserId : ea7944ca-c77f-4491-8a51-aa713c646da7
     */

    private String ShopName;
    private String Cover;
    private String WebAddress;
    private int CountryId;
    private int ProvinceId;
    private int DistrictId;
    private String Description;

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
}

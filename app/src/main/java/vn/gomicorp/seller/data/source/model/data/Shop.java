package vn.gomicorp.seller.data.source.model.data;

/**
 * Created by KHOI LE on 3/20/2020.
 */
public class Shop {

    /**
     * Id : f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e
     * UserId : 00000000-0000-0000-0000-000000000000
     * Cover :
     * Avatar :
     * ShopName : Khoi Le
     * WebAddress : khoile000
     * Description : fashion
     * CountryId : 1
     * ProvinceId : 2
     * DistrictId : 215
     * CreatedDate : 2020-03-20T12:22:42.63
     * ProductId : 00000000-0000-0000-0000-000000000000
     * CountryName : Việt Nam
     * ProvinceName : Hồ Chí Minh
     * DistrictName : Quận Tân Bình
     * PointBalance : 0
     * Visits : 0
     * Follows : 0
     * Followed : false
     */

    private String Id;
    private String UserId;
    private String Cover;
    private String Avatar;
    private String ShopName;
    private String WebAddress;
    private String Description;
    private int CountryId;
    private int ProvinceId;
    private int DistrictId;
    private String CreatedDate;
    private String ProductId;
    private String CountryName;
    private String ProvinceName;
    private String DistrictName;
    private int PointBalance;
    private int Visits;
    private int Follows;
    private boolean Followed;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String Cover) {
        this.Cover = Cover;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getWebAddress() {
        return WebAddress;
    }

    public void setWebAddress(String WebAddress) {
        this.WebAddress = WebAddress;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
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

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String ProductId) {
        this.ProductId = ProductId;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String CountryName) {
        this.CountryName = CountryName;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String DistrictName) {
        this.DistrictName = DistrictName;
    }

    public int getPointBalance() {
        return PointBalance;
    }

    public void setPointBalance(int PointBalance) {
        this.PointBalance = PointBalance;
    }

    public int getVisits() {
        return Visits;
    }

    public void setVisits(int Visits) {
        this.Visits = Visits;
    }

    public int getFollows() {
        return Follows;
    }

    public void setFollows(int Follows) {
        this.Follows = Follows;
    }

    public boolean isFollowed() {
        return Followed;
    }

    public void setFollowed(boolean Followed) {
        this.Followed = Followed;
    }
}

package vn.gomisellers.apps.data.source.model.data;

/**
 * Created by KHOI LE on 4/29/2020.
 */
public class OrderDetail {

    /**
     * Id : 00000000-0000-0000-0000-000000000000
     * OrderId : 00000000-0000-0000-0000-000000000000
     * ProductId : e6196bf4-6f32-4a51-b8d4-640d27d3f09f
     * DetailId : 0
     * Name : Dầu Gội Ngăn Rụng Tóc Aheads Premium Hidden Therapy Shampoo
     * Title :
     * SKU : AHPRHITS43001
     * MediaPath : http://192.168.1.33:2526/Product/gomi_1bff68d9-9766-4052-aa0b-417c1a404d0c-637213342121682313.jpg
     * Price : 426.000
     * Quantity : 5
     * PriceTemp : 2.130.000
     */

    private String Id;
    private String OrderId;
    private String ProductId;
    private int DetailId;
    private String Name;
    private String Title;
    private String SKU;
    private String MediaPath;
    private double Price;
    private int Quantity;
    private String PriceTemp;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String ProductId) {
        this.ProductId = ProductId;
    }

    public int getDetailId() {
        return DetailId;
    }

    public void setDetailId(int DetailId) {
        this.DetailId = DetailId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getMediaPath() {
        return MediaPath;
    }

    public void setMediaPath(String MediaPath) {
        this.MediaPath = MediaPath;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getPriceTemp() {
        return PriceTemp;
    }

    public void setPriceTemp(String PriceTemp) {
        this.PriceTemp = PriceTemp;
    }
}

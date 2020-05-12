package vn.gomisellers.apps.data.source.model.data;

/**
 * Created by KHOI LE on 4/29/2020.
 */
public class OrderDetail {

    /**
     * Id : 00000000-0000-0000-0000-000000000000
     * OrderId : 00000000-0000-0000-0000-000000000000
     * ProductId : ff961d03-fa40-4988-967a-3cf4c087d176
     * DetailId : 0
     * Name : Lăn Loại Bỏ Tế Bào Chết Và Dưỡng Ẩm Cho Bàn Chân - Athis BHA+ Foot Stick Softening
     * Title :
     * SKU : ATFOSS0001601
     * MediaPath : http://192.168.1.24:2526/Product/gomi_ace40c39-e618-4f72-b125-8fa21c1f5c5b-637207134087619844.jpg
     * Price : 199000
     * Quantity : 1
     * PriceTemp : 199000
     */

    private String Id;
    private String OrderId;
    private String ProductId;
    private int DetailId;
    private String Name;
    private String Title;
    private String SKU;
    private String MediaPath;
    private int Price;
    private int Quantity;
    private int PriceTemp;

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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public int getPriceTemp() {
        return PriceTemp;
    }

    public void setPriceTemp(int PriceTemp) {
        this.PriceTemp = PriceTemp;
    }
}

package vn.gomisellers.apps.data.source.model.data;

import java.util.List;

import vn.gomisellers.apps.main.mypage.order.detail.OrderDetail;

/**
 * Created by KHOI LE on 4/28/2020.
 */
public class Order {
    /**
     * CustomerId : 00000000-0000-0000-0000-000000000000
     * ShopId : 00000000-0000-0000-0000-000000000000
     * OrderCode :
     * FullName :
     * Phone :
     * AddressLine :
     */

    private String CustomerId;
    private String ShopId;
    private String OrderCode;
    private String FullName;
    private String Phone;
    private String AddressLine;

    private String id;
    private String name;
    private long dateCreate;
    private String thumnail;
    private double price;
    private int count;
    private List<OrderDetail> orderDetails;

    public Order(String id, String name, long dateCreate, String thumnail, double price, int count) {
        this.id = id;
        this.name = name;
        this.dateCreate = dateCreate;
        this.thumnail = thumnail;
        this.price = price;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddressLine() {
        return AddressLine;
    }

    public void setAddressLine(String AddressLine) {
        this.AddressLine = AddressLine;
    }
}

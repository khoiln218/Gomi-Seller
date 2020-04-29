package vn.gomisellers.apps.main.mypage.order.detail;

import vn.gomisellers.apps.data.source.model.data.Product;

/**
 * Created by KHOI LE on 4/29/2020.
 */
public class OrderDetail {
    private String id;
    private Product product;
    private String name;
    private double price;
    private int count;

    public OrderDetail(String id, Product product, String name, double price, int count) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

package vn.gomisellers.apps.data.source.model.data;

/**
 * Created by KHOI LE on 4/28/2020.
 */
public class Order {
    private String id;
    private String name;
    private long dateCreate;
    private String thumnail;
    private double price;
    private int count;

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
}

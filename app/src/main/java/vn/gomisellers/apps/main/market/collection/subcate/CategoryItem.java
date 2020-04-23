package vn.gomisellers.apps.main.market.collection.subcate;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class CategoryItem {
    private int type;
    private int id;
    private String name;

    public CategoryItem(int type, int id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

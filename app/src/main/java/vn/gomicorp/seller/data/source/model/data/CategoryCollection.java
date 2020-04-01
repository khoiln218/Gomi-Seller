package vn.gomicorp.seller.data.source.model.data;

import java.util.List;

/**
 * Created by KHOI LE on 4/1/2020.
 */
public class CategoryCollection {
    private List<Category> CateList;
    private List<Product> ProductList;

    public List<Category> getCateList() {
        return CateList;
    }

    public void setCateList(List<Category> CateList) {
        this.CateList = CateList;
    }

    public List<Product> getProductList() {
        return ProductList;
    }

    public void setProductList(List<Product> ProductList) {
        this.ProductList = ProductList;
    }
}

package vn.gomicorp.seller.data.source.model.data;

import java.util.List;

/**
 * Created by KHOI LE on 3/24/2020.
 */
public class Introduce {

    /**
     * BannerList : []
     * MegaCateList : [{"Id":1,"Name":"Trang sức - Phụ kiện","Icon":"ic_trang-suc-phu-kien.png"},{"Id":2,"Name":"Thời trang - Phụ kiện","Icon":"ic_thoi-trang-phu-kien.png"},{"Id":3,"Name":"Làm đẹp - Sức khỏe","Icon":"ic_lam-dep-suc-khoe.png"},{"Id":4,"Name":"Nhà cửa đời sống","Icon":"ic_nha-cua-doi-song.png"},{"Id":5,"Name":"Dành cho thú cưng","Icon":"ic_danh-cho-thu-cung.png"},{"Id":6,"Name":"Laptop - Thiết bị IT","Icon":"ic_laptop-thiet-bi-it.png"},{"Id":7,"Name":"Phụ kiện - Thiết bị số","Icon":"ic_phu-kien-thiet-bi-so.png"},{"Id":8,"Name":"Thể thao - Dã ngoại","Icon":"ic_the-thao-da-ngoai.png"},{"Id":9,"Name":"Điện thoại - Máy tính bảng","Icon":"ic_dien-thoai-may-tinh-bang.png"},{"Id":10,"Name":"Đồ chơi - Mẹ & Bé","Icon":"ic_do-choi-me-be.png"},{"Id":11,"Name":"Điện gia dụng","Icon":"ic_dien-gia-dung.png"},{"Id":12,"Name":"Thực phẩm","Icon":"ic_thuc-pham.png"},{"Id":13,"Name":"Hỗ trợ tình dục","Icon":"ic_ho-tro-tinh-duc.png"}]
     * CollectionList : [{"Id":1,"Name":"Sản phẩm mới","ProductList":[{"Id":"347a551a-4744-4799-ba84-3613950362d3","Name":"Xịt Trị Mụn Lưng Aetem Cleanback Mist","Description":null,"Thumbnail":"http://192.168.0.149:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg","Url":"xit-tri-mun-lung-aetem-cleanback-mist/347a551a-4744-4799-ba84-3613950362d3","BrandId":0,"DetailId":0,"BrandName":null,"BrandCountry":null,"Price":500000,"MarketPrice":1000000,"SaleOff":50,"Profit":50000,"AsSelling":0,"AvgRating":0,"Purchases":0,"VideoReviews":0,"Comments":0,"AlsoSell":0,"Reviews":0,"SellerVideo":null,"ImageList":null,"Type":null,"AttributeList":null,"VideoReviewList":null,"VariantList":null,"CommentList":null,"TotalPage":0}]},{"Id":2,"Name":"Sản phẩm tiêu biểu","ProductList":[{"Id":"347a551a-4744-4799-ba84-3613950362d3","Name":"Xịt Trị Mụn Lưng Aetem Cleanback Mist","Description":null,"Thumbnail":"http://192.168.0.149:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg","Url":"xit-tri-mun-lung-aetem-cleanback-mist/347a551a-4744-4799-ba84-3613950362d3","BrandId":0,"DetailId":0,"BrandName":null,"BrandCountry":null,"Price":500000,"MarketPrice":1000000,"SaleOff":50,"Profit":50000,"AsSelling":0,"AvgRating":0,"Purchases":0,"VideoReviews":0,"Comments":0,"AlsoSell":0,"Reviews":0,"SellerVideo":null,"ImageList":null,"Type":null,"AttributeList":null,"VideoReviewList":null,"VariantList":null,"CommentList":null,"TotalPage":0}]}]
     * ProductSeen : {"ProductList":[{"Id":"347a551a-4744-4799-ba84-3613950362d3","Name":"Xịt Trị Mụn Lưng Aetem Cleanback Mist","Description":null,"Thumbnail":"http://192.168.0.149:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg","Url":"xit-tri-mun-lung-aetem-cleanback-mist/347a551a-4744-4799-ba84-3613950362d3","BrandId":0,"DetailId":0,"BrandName":null,"BrandCountry":null,"Price":500000,"MarketPrice":1000000,"SaleOff":50,"Profit":50000,"AsSelling":0,"AvgRating":0,"Purchases":0,"VideoReviews":0,"Comments":0,"AlsoSell":0,"Reviews":0,"SellerVideo":null,"ImageList":null,"Type":null,"AttributeList":null,"VideoReviewList":null,"VariantList":null,"CommentList":null,"TotalPage":0}]}
     */

    private ProductSeenBean ProductSeen;
    private List<Banner> BannerList;
    private List<MegaCateListBean> MegaCateList;
    private List<CollectionListBean> CollectionList;

    public ProductSeenBean getProductSeen() {
        return ProductSeen;
    }

    public void setProductSeen(ProductSeenBean ProductSeen) {
        this.ProductSeen = ProductSeen;
    }

    public List<Banner> getBannerList() {
        return BannerList;
    }

    public void setBannerList(List<Banner> BannerList) {
        this.BannerList = BannerList;
    }

    public List<MegaCateListBean> getMegaCateList() {
        return MegaCateList;
    }

    public void setMegaCateList(List<MegaCateListBean> MegaCateList) {
        this.MegaCateList = MegaCateList;
    }

    public List<CollectionListBean> getCollectionList() {
        return CollectionList;
    }

    public void setCollectionList(List<CollectionListBean> CollectionList) {
        this.CollectionList = CollectionList;
    }

    public static class ProductSeenBean {
        private List<Product> ProductList;

        public List<Product> getProductList() {
            return ProductList;
        }

        public void setProductList(List<Product> ProductList) {
            this.ProductList = ProductList;
        }
    }

    public static class CollectionListBean {
        /**
         * Id : 1
         * Name : Sản phẩm mới
         * ProductList : [{"Id":"347a551a-4744-4799-ba84-3613950362d3","Name":"Xịt Trị Mụn Lưng Aetem Cleanback Mist","Description":null,"Thumbnail":"http://192.168.0.149:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg","Url":"xit-tri-mun-lung-aetem-cleanback-mist/347a551a-4744-4799-ba84-3613950362d3","BrandId":0,"DetailId":0,"BrandName":null,"BrandCountry":null,"Price":500000,"MarketPrice":1000000,"SaleOff":50,"Profit":50000,"AsSelling":0,"AvgRating":0,"Purchases":0,"VideoReviews":0,"Comments":0,"AlsoSell":0,"Reviews":0,"SellerVideo":null,"ImageList":null,"Type":null,"AttributeList":null,"VideoReviewList":null,"VariantList":null,"CommentList":null,"TotalPage":0}]
         */

        private int Id;
        private String Name;
        private List<Product> ProductList;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public List<Product> getProductList() {
            return ProductList;
        }

        public void setProductList(List<Product> ProductList) {
            this.ProductList = ProductList;
        }
    }
}

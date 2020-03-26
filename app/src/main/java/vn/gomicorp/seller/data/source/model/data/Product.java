package vn.gomicorp.seller.data.source.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class Product implements Parcelable {

    /**
     * Id : 00000000-0000-0000-0000-000000000000
     * Name : null
     * Description : null
     * Thumbnail : null
     * Url : null
     * BrandId : 0
     * DetailId : 0
     * BrandName : null
     * BrandCountry : null
     * Price : 0
     * MarketPrice : 0
     * SaleOff : 0
     * Profit : 0
     * AsSelling : 0
     * AvgRating : 0
     * Purchases : 0
     * VideoReviews : 0
     * Comments : 0
     * AlsoSell : 0
     * Reviews : 0
     * SellerVideo : null
     * ImageList : null
     * Type : null
     * AttributeList : null
     * VideoReviewList : null
     * VariantList : null
     * CommentList : null
     * TotalPage : 0
     */

    private String Id;
    private String Name;
    private String Description;
    private String Thumbnail;
    private String Url;
    private int BrandId;
    private String BrandName;
    private String BrandCountry;
    private double Price;
    private double MarketPrice;
    private int SaleOff;
    private double Profit;
    private int IsSelling;
    private List<String> ImageList;
    private List<Attribute> AttributeList;

    protected Product(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        Description = in.readString();
        Thumbnail = in.readString();
        Url = in.readString();
        BrandId = in.readInt();
        BrandName = in.readString();
        BrandCountry = in.readString();
        Price = in.readInt();
        MarketPrice = in.readInt();
        SaleOff = in.readInt();
        Profit = in.readInt();
        IsSelling = in.readInt();
        ImageList = in.createStringArrayList();
        AttributeList = in.createTypedArrayList(Attribute.CREATOR);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String Thumbnail) {
        this.Thumbnail = Thumbnail;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int BrandId) {
        this.BrandId = BrandId;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    public Object getBrandCountry() {
        return BrandCountry;
    }

    public void setBrandCountry(String BrandCountry) {
        this.BrandCountry = BrandCountry;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public double getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(double MarketPrice) {
        this.MarketPrice = MarketPrice;
    }

    public int getSaleOff() {
        return SaleOff;
    }

    public void setSaleOff(int SaleOff) {
        this.SaleOff = SaleOff;
    }

    public double getProfit() {
        return Profit;
    }

    public void setProfit(double Profit) {
        this.Profit = Profit;
    }

    public int getIsSelling() {
        return IsSelling;
    }

    public void setIsSelling(int AsSelling) {
        this.IsSelling = AsSelling;
    }

    public List<String> getImageList() {
        return ImageList;
    }

    public void setImageList(List<String> ImageList) {
        this.ImageList = ImageList;
    }

    public List<Attribute> getAttributeList() {
        return AttributeList;
    }

    public void setAttributeList(List<Attribute> AttributeList) {
        this.AttributeList = AttributeList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Name);
        dest.writeString(Description);
        dest.writeString(Thumbnail);
        dest.writeString(Url);
        dest.writeInt(BrandId);
        dest.writeString(BrandName);
        dest.writeString(BrandCountry);
        dest.writeDouble(Price);
        dest.writeDouble(MarketPrice);
        dest.writeInt(SaleOff);
        dest.writeDouble(Profit);
        dest.writeInt(IsSelling);
        dest.writeStringList(ImageList);
        dest.writeTypedList(AttributeList);
    }
}

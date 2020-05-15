package vn.gomisellers.apps.data.source.model.data;

/**
 * Created by KHOI LE on 5/6/2020.
 */
public class Notification {

    /**
     * Id : d2f7388f-7b47-42c2-983e-9ea19a6b0cb4
     * Title : Có đơn hàng mới!
     * Content : Đoàn Nam Bảo Khương đã mua Bộ 2 Bàn Chải Thành Phần Than Đen Paul Medison Lacha ToothBrush
     * ObjectId : 2071e357-20fd-4a22-8bcc-6b30d836106f
     * CreatedDate : Hôm nay 10:13
     * Type : 23
     * Status : 0
     * TotalPage : 1
     * HasChild : false
     * Promotion : 0
     * News : 0
     * Wallet : 0
     * Order : 0
     */

    private String Id;
    private String Title;
    private String Content;
    private String ObjectId;
    private String CreatedDate;
    private int Type;
    private int Status;
    private int TotalPage;
    private boolean HasChild;
    private int Promotion;
    private int News;
    private int Wallet;
    private int Order;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getObjectId() {
        return ObjectId;
    }

    public void setObjectId(String ObjectId) {
        this.ObjectId = ObjectId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int TotalPage) {
        this.TotalPage = TotalPage;
    }

    public boolean isHasChild() {
        return HasChild;
    }

    public void setHasChild(boolean HasChild) {
        this.HasChild = HasChild;
    }

    public int getPromotion() {
        return Promotion;
    }

    public void setPromotion(int Promotion) {
        this.Promotion = Promotion;
    }

    public int getNews() {
        return News;
    }

    public void setNews(int News) {
        this.News = News;
    }

    public int getWallet() {
        return Wallet;
    }

    public void setWallet(int Wallet) {
        this.Wallet = Wallet;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int Order) {
        this.Order = Order;
    }
}

package vn.gomisellers.apps.data.source.model.data;

import java.util.List;

/**
 * Created by KHOI LE on 4/28/2020.
 */
public class Order {

    /**
     * Id : 95e89e4a-8d0a-450e-a8f3-4a066776bf09
     * FullName : Khoi Le 02
     * Note :
     * OrderCode : #100015
     * CreatedDate : Hôm qua 17:09
     * AddressLine :
     * ProvinceId : 0
     * ProvinceName :
     * DistrictId : 0
     * DistrictName :
     * WardId : 0
     * WardName :
     * ConfirmStt : 0
     * DeliveryStt : 0
     * TransactionStt : 0
     * CODStt : 0
     * Status : 0
     * StatusName :
     * TotalPrice : 2.2368E7
     * ProfitPerOrder : 2236800
     * TotalPage : 2
     * TotalRows : 0
     * listItems : []
     */

    private String Id;
    private String FullName;
    private String Note;
    private String OrderCode;
    private String CreatedDate;
    private String AddressLine;
    private int ProvinceId;
    private String ProvinceName;
    private int DistrictId;
    private String DistrictName;
    private int WardId;
    private String WardName;
    private int ConfirmStt;
    private int DeliveryStt;
    private int TransactionStt;
    private int CODStt;
    private int Status;
    private String StatusName;
    private double TotalPrice;
    private int ProfitPerOrder;
    private int TotalPage;
    private int TotalRows;
    private List<OrderDetail> listItems;

    private String Description;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getAddressLine() {
        return AddressLine;
    }

    public void setAddressLine(String AddressLine) {
        this.AddressLine = AddressLine;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int ProvinceId) {
        this.ProvinceId = ProvinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int DistrictId) {
        this.DistrictId = DistrictId;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String DistrictName) {
        this.DistrictName = DistrictName;
    }

    public int getWardId() {
        return WardId;
    }

    public void setWardId(int WardId) {
        this.WardId = WardId;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String WardName) {
        this.WardName = WardName;
    }

    public int getConfirmStt() {
        return ConfirmStt;
    }

    public void setConfirmStt(int ConfirmStt) {
        this.ConfirmStt = ConfirmStt;
    }

    public int getDeliveryStt() {
        return DeliveryStt;
    }

    public void setDeliveryStt(int DeliveryStt) {
        this.DeliveryStt = DeliveryStt;
    }

    public int getTransactionStt() {
        return TransactionStt;
    }

    public void setTransactionStt(int TransactionStt) {
        this.TransactionStt = TransactionStt;
    }

    public int getCODStt() {
        return CODStt;
    }

    public void setCODStt(int CODStt) {
        this.CODStt = CODStt;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public int getProfitPerOrder() {
        return ProfitPerOrder;
    }

    public void setProfitPerOrder(int ProfitPerOrder) {
        this.ProfitPerOrder = ProfitPerOrder;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int TotalPage) {
        this.TotalPage = TotalPage;
    }

    public int getTotalRows() {
        return TotalRows;
    }

    public void setTotalRows(int TotalRows) {
        this.TotalRows = TotalRows;
    }

    public List<OrderDetail> getListItems() {
        return listItems;
    }

    public void setListItems(List<OrderDetail> listItems) {
        this.listItems = listItems;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}

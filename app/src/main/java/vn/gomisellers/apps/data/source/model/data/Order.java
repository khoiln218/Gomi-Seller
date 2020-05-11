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
     * CreatedDate : Hôm nay 17:09
     * AddressLine :
     * ProvinceId : 0
     * ProvinceName :
     * DistrictId : 0
     * DistrictName :
     * WardId : 0
     * WardName :
     * ConfirmStt : 0
     * ConfirmName :
     * DeliveryStt : 0
     * DeliveryName :
     * TransactionStt : 0
     * TransactionName :
     * CODStt : 0
     * CODSName :
     * Status : 0
     * StatusName : Mới
     * TotalPrice : 22.368.000
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
    private String ConfirmName;
    private int DeliveryStt;
    private String DeliveryName;
    private int TransactionStt;
    private String TransactionName;
    private int CODStt;
    private String CODSName;
    private int Status;
    private String StatusName;
    private String TotalPrice;
    private int ProfitPerOrder;
    private int TotalPage;
    private int TotalRows;
    private List<OrderDetail> listItems;

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

    public String getConfirmName() {
        return ConfirmName;
    }

    public void setConfirmName(String ConfirmName) {
        this.ConfirmName = ConfirmName;
    }

    public int getDeliveryStt() {
        return DeliveryStt;
    }

    public void setDeliveryStt(int DeliveryStt) {
        this.DeliveryStt = DeliveryStt;
    }

    public String getDeliveryName() {
        return DeliveryName;
    }

    public void setDeliveryName(String DeliveryName) {
        this.DeliveryName = DeliveryName;
    }

    public int getTransactionStt() {
        return TransactionStt;
    }

    public void setTransactionStt(int TransactionStt) {
        this.TransactionStt = TransactionStt;
    }

    public String getTransactionName() {
        return TransactionName;
    }

    public void setTransactionName(String TransactionName) {
        this.TransactionName = TransactionName;
    }

    public int getCODStt() {
        return CODStt;
    }

    public void setCODStt(int CODStt) {
        this.CODStt = CODStt;
    }

    public String getCODSName() {
        return CODSName;
    }

    public void setCODSName(String CODSName) {
        this.CODSName = CODSName;
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

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
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
}

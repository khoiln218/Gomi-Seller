package vn.gomisellers.apps.data.source.model.data;

import java.util.List;

import vn.gomisellers.apps.main.mypage.order.detail.OrderDetail;

/**
 * Created by KHOI LE on 4/28/2020.
 */
public class Order {

    /**
     * Id : 3459fa9a-cc95-4953-9669-7add22db4610
     * FullName : Đoàn Khương
     * Note :
     * OrderCode : #100002
     * CreatedDate : 07/05/2020 15:49
     * ConfirmStt : 0
     * DeliveryStt : 0
     * TransactionStt : 0
     * CODStt : 0
     * Status : 0
     * TotalPrice : 400.000
     * ProfitPerOrder : 40000
     * TotalPage : 1
     * TotalRows : 0
     * listItems : null
     */

    private String Id;
    private String FullName;
    private String Note;
    private String OrderCode;
    private String CreatedDate;
    private int ConfirmStt;
    private int DeliveryStt;
    private int TransactionStt;
    private int CODStt;
    private int Status;
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

package vn.gomicorp.seller.data.source.model.api;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class VerifyUrlRequest extends BaseRequest {

    /**
     * WebAddress : khoile000
     */

    private String WebAddress;

    public String getWebAddress() {
        return WebAddress;
    }

    public void setWebAddress(String WebAddress) {
        this.WebAddress = WebAddress;
    }
}
